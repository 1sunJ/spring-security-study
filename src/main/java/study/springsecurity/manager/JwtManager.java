package study.springsecurity.manager;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


@Component
@RequiredArgsConstructor
public class JwtManager {

    @Value("${spring.application.name}")
    private String issuer;

    @Value("{jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-expiration-time}")
    private Long accessExpiration;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }



}
