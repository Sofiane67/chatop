package fr.chatop.config.jwt;

import fr.chatop.exception.JwtTokenExpiredException;
import fr.chatop.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {
    private final String ENCRIPTION_KEY;
    private final int EXPIRATION_TIME;
    private AuthService authService;

    public JwtService(@Value("${JWT_SECRET}") String encryptionKey, @Value("$" +
        "{JWT_EXPIRATION}") int expirationTime, AuthService authService) {
        this.ENCRIPTION_KEY = encryptionKey;
        this.EXPIRATION_TIME = expirationTime;
        this.authService = authService;
    }

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

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = this.parseToken(token).getExpiration();
            if (expirationDate.before(new Date())) {
                throw new JwtTokenExpiredException();
            }
            return false;
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException();
        }
    }

    private Map<String, String> generateJwt(UserDetails userDetails) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + EXPIRATION_TIME;

        final Map<String, Object> claims = Map.of(
                "email", userDetails.getUsername(),
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
