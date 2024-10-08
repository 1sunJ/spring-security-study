package study.springsecurity.auth.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class BannedToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banned_token_id")
    private Long id;

    private String token;

    @CreationTimestamp
    private LocalDateTime bannedDateTime;

    public BannedToken(String token) {
        this.token = token;
    }

}
