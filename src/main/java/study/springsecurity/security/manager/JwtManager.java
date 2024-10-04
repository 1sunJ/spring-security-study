package study.springsecurity.security.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import study.springsecurity.security.domain.enums.TokenType;
import study.springsecurity.security.exception.NotValidatedTokenException;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtManager {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.access-expiration-time}")
    private Long ACCESS_EXPIRATION_TIME;

    @Value("${jwt.refresh-expiration-time}")
    private Long REFRESH_EXPIRATION_TIME;

    public String generateToken(Authentication authentication, TokenType tokenType) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return generateToken(authorities, authentication.getName(), tokenType);
    }

    public String generateToken(String authorities, String email, TokenType tokenType) {
        return Jwts.builder()
                .signWith(getSecretKey())
                .subject(email)
                .claim("tokenType", tokenType)
                .claim("authorities", authorities)
                .issuedAt(new Date())
                .expiration(getExpirationDate(tokenType))
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception e) {
            log.error("★★★ fail to validate token ★★★", e);
            throw new NotValidatedTokenException();
        }
    }

    public String extractToken(HttpServletRequest request) {
        return (String) request.getHeader("Authorization");
    }

    public String getEmail(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return payload.getSubject();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        String authorities = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("authorities", String.class);

        return AuthorityUtils.createAuthorityList(authorities.split(","));
    }

    private Date getExpirationDate(TokenType tokenType) {
       long expirationTime = tokenType.equals(TokenType.ACCESS_TOKEN) ? ACCESS_EXPIRATION_TIME : REFRESH_EXPIRATION_TIME;
       return new Date(new Date().getTime() + expirationTime);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY));
    }

}
