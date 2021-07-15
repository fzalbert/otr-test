package com.example.autorizeservice.config;

import com.example.autorizeservice.dto.AuthDto;
import com.example.autorizeservice.dto.LoginDto;
import com.example.autorizeservice.dto.ResponseModel;
import com.example.autorizeservice.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.autorizeservice.utils.SecurityConstants.AUTHORIZATION_HEADER;
import static com.example.autorizeservice.utils.SecurityConstants.BEARER_PREFIX;

public class JwtClientFilter extends AbstractAuthenticationProcessingFilter {

    private final String signingKey;
    private final String urlClient;

    public JwtClientFilter(AuthenticationManager authenticationManager, String signingKey, String urlClient) {
        super(new AntPathRequestMatcher("/api/login/client", "POST"));
        setAuthenticationManager(authenticationManager);
        this.signingKey = signingKey;
        this.urlClient = urlClient;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        var objectMapper = new ObjectMapper();

        LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
        var res = getResponse(loginDto.getUsername(), loginDto.getPassword());

        if(res.status != HttpStatus.OK) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(res.content);
            response.getWriter().flush();
            return null;
        }

        var client = objectMapper.readValue(res.content, UserDto.class);

        if (client == null) {
            response.sendError(401);
            return null;
        }

        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(client.getId().toString()));
        list.add(new SimpleGrantedAuthority(client.getName()));
        list.add(new SimpleGrantedAuthority(client.getEmail()));
        list.add(new SimpleGrantedAuthority("ROLE_" + client.getRole()));

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


    private ResponseModel getResponse(String login, String password) {
        ResponseModel model = new ResponseModel();
        AuthDto body = new AuthDto(login, password);
        var restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                model.setStatus(clientHttpResponse.getStatusCode());
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                model.setStatus(clientHttpResponse.getStatusCode());
            }
        });
        var result = restTemplate.postForObject(urlClient, body, String.class);
        model.content = result;
        return model;
    }
}
