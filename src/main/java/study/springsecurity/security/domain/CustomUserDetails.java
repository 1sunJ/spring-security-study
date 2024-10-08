package study.springsecurity.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.springsecurity.member.Member;
import study.springsecurity.security.exception.NotAuthenticatedException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String email;

    @Setter
    private Set<GrantedAuthority> authorities = new HashSet<>();

    public CustomUserDetails(String email, Set<GrantedAuthority> authorities) {
        this.email = email;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * not used
     */
    @Override
    public String getPassword() {
        return null;
    }

    public static String getEmail(CustomUserDetails userDetails, boolean required) {
        if (userDetails != null) {
            return userDetails.getEmail();
        } else {
            if (required) {
                throw new NotAuthenticatedException();
            } else {
                return null;
            }
        }
    }

}
