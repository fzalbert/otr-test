package com.example.autorizeservice.service.impl;

import com.example.autorizeservice.dto.JwtParseResponseDto;
import com.example.autorizeservice.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class JwtServiceImpl implements JwtService {
    private final String signingKey;

    @Autowired
    public JwtServiceImpl(@Value("${security.jwt.signing-key}") String signingKey) {
        this.signingKey = signingKey;
    }

    @Override
    public JwtParseResponseDto parseJwt(String token) {
        Objects.requireNonNull(token);

        Claims claims = Jwts.parser()
                .setSigningKey(signingKey.getBytes())
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        try {
            List<String> authorities = claims.get("authorities", List.class);
            Long clientId = Long.parseLong(authorities.get(0));
            return new JwtParseResponseDto(clientId, authorities);
        } catch (Exception ex) {
            //throw new UnauthorisedException(ex.getLocalizedMessage(), TokenInvalid.DEVELOPER_MESSAGE);
            return null;
        }
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(signingKey.getBytes())
                    .parseClaimsJws(token);

            return (!claims.getBody().getExpiration().before(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
