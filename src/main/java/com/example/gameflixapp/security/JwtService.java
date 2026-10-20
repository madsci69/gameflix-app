package com.example.gameflixapp.security;

import com.nimbusds.jose.JWSHeader;
import jakarta.persistence.ManyToMany;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final long expirationMS;

    public JwtService(JwtEncoder jwtEncoder, @Value("${jwt.expiration-ms}") long expirationMS) {
        this.jwtEncoder = jwtEncoder;
        this.expirationMS = expirationMS;
    }

    public String generateToken(String email) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("gameflix")
                .subject(email)
                .issuedAt(now)
                .expiresAt(now.plusMillis(expirationMS))
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
