package study.springsecurity.security.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.springsecurity.member.Member;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;

    public CustomUserDetails(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
