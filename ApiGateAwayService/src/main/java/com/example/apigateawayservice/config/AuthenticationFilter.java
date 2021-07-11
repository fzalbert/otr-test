package com.example.apigateawayservice.config;

import com.example.apigateawayservice.dto.JwtParseRequestDto;
import com.example.apigateawayservice.dto.JwtParseResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.example.apigateawayservice.utils.SecurityConstants.AUTHORIZATION_HEADER;
import static com.example.apigateawayservice.utils.SecurityConstants.BEARER_PREFIX;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${security.jwt.parse.url}")
    private String JWT_PARSE_URL;

    private final RestTemplate restTemplate;

    public AuthenticationFilter() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token != null) {
            token = token.replace(BEARER_PREFIX  + " ", "");
            try {
                JwtParseResponseDto responseDto = parseJwt(token);

                if (responseDto == null){
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                }

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        responseDto.getUsername(),
                        null,
                        responseDto.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignore) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

/*    private Boolean isValidJwt(String token) {
        String url = JWT_VALID_URL + "?token=" + token;
        return restTemplate.getForObject(url, Boolean.class);
    }*/

    private JwtParseResponseDto parseJwt(String token) {
        return restTemplate.postForObject(JWT_PARSE_URL, new JwtParseRequestDto(token),
                JwtParseResponseDto.class);
    }
}
