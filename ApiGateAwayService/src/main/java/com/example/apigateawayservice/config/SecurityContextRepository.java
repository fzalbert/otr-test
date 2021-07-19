package com.example.apigateawayservice.config;

import com.example.apigateawayservice.dto.JwtParseRequestDto;
import com.example.apigateawayservice.dto.JwtParseResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.util.stream.Collectors;

import static com.example.apigateawayservice.utils.SecurityConstants.AUTHORIZATION_HEADER;
import static com.example.apigateawayservice.utils.SecurityConstants.BEARER_PREFIX;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Value("${security.jwt.parse.url}")
    private String jwtParse;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);

        if (token != null) {
            token = token.replace(BEARER_PREFIX  + " ", "");
            try {

                JwtParseResponseDto responseDto = parseJwt(token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        responseDto.getAuthorities().get(2),
                        null,
                        responseDto.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );

                return Mono.justOrEmpty(new SecurityContextImpl(auth));

            } catch (Exception ignore) {
                SecurityContextHolder.clearContext();
            }
        }
        return Mono.empty();
    }

    private JwtParseResponseDto parseJwt(String token) {
        return restTemplate.postForObject(jwtParse, new JwtParseRequestDto(token),
                JwtParseResponseDto.class);
    }
}
