package com.example.autorizeservice.config;

import com.example.autorizeservice.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String HEADER = "Authorization";

    public static final String HEADER_VALUE_PREFIX = "Bearer";

    private final String signingKey;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, String signingKey) {
        super(new AntPathRequestMatcher("/v1/login", "POST"));
        setAuthenticationManager(authenticationManager);
        this.signingKey = signingKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        var checkUser = CheckUser(loginDto.getUsername(), loginDto.getPassword());

        if (!checkUser) {
            response.sendError(401);
            return null;
        }

        var user = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
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
        response.addHeader(HEADER, HEADER_VALUE_PREFIX + " " + token);
    }

    /**
     * Test method
     **/
    private Boolean CheckUser(String login, String password) {
        final String url = "http://localhost:5555/wh/clients/auth?login="+login+"&password="+password;

        var restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Boolean.class);
    }
}
