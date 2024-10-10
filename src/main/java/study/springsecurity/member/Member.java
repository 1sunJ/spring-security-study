package study.springsecurity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private Long kakaoId;

    private String name;

    private String email;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<GrantedAuthority> authorities = new HashSet<>();

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Member(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public static Member createByKakao(Long kakaoId) {
        return new Member(kakaoId);
    }

    public void addAuthority(GrantedAuthority authority) {
        this.authorities.add(authority);
    }


}
