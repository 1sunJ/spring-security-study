package study.springsecurity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

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

    private String name;

    private String email;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<GrantedAuthority> authorities = new HashSet<>();

}
