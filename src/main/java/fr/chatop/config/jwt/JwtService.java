package fr.chatop.config.jwt;

import fr.chatop.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@Service
public class JwtService {
    private final String ENCRIPTION_KEY = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
    private AuthService authService;

    public Map<String, String> generate(String username) {
        UserDetails userDetails = this.authService.loadUserByUsername(username);
        return this.generateJwt(userDetails);
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
            .setSigningKey(this.getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public String extractUsername(String token) {
        return this.parseToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.parseToken(token).getExpiration();
        return expirationDate.before(new Date());
    }

    private Map<String, String> generateJwt(UserDetails userDetails) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "nom", userDetails.getUsername(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, userDetails.getUsername()
        );

        final String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("token", token);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }
}
