package com.example.autorizeservice.config;

import com.example.autorizeservice.dto.AuthDto;
import com.example.autorizeservice.dto.LoginDto;
import com.example.autorizeservice.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.autorizeservice.utils.SecurityConstants.AUTHORIZATION_HEADER;
import static com.example.autorizeservice.utils.SecurityConstants.BEARER_PREFIX;

public class JwtEmployeeFilter extends AbstractAuthenticationProcessingFilter {

    private final String signingKey;
    private final String urlEmployee;

    public JwtEmployeeFilter(AuthenticationManager authenticationManager, String signingKey, String urlEmployee) {
        super(new AntPathRequestMatcher("/api/login/employee", "POST"));
        setAuthenticationManager(authenticationManager);
        this.signingKey = signingKey;
        this.urlEmployee = urlEmployee;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        var employee = CheckUser(loginDto.getUsername(), loginDto.getPassword());

        if (employee == null) {
            response.sendError(401);
            return null;
        }

        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(employee.getId().toString()));
        list.add(new SimpleGrantedAuthority(employee.getName()));
        list.add(new SimpleGrantedAuthority(employee.getEmail()));
        list.add(new SimpleGrantedAuthority("ROLE_" + employee.getRole()));

        var user = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword(),
                list
        );

        SecurityContextHolder.getContext().setAuthentication(user);
        return user;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) {
        Instant now = Instant.now();

        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(8 * 60 * 60))) // token expires in 8 hours
                .signWith(SignatureAlgorithm.HS256, signingKey.getBytes())
                .compact();

        response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + " " + token);
        try {
            response.getWriter().write(token);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserDto CheckUser(String login, String password) {
        AuthDto body = new AuthDto(login, password);
        var restTemplate = new RestTemplate();
        return restTemplate.postForObject(urlEmployee, body, UserDto.class);
    }
}
