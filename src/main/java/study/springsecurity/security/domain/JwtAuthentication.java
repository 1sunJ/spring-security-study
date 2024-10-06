package study.springsecurity.security.domain;

import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JwtAuthentication implements Authentication {

    private String jwtToken;
    @Setter
    private CustomUserDetails customUserDetails;

    private boolean isAuthenticated = false;

    public JwtAuthentication(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public String getCredentials() {
        return jwtToken;
    }

    @Override
    public CustomUserDetails getDetails() {
        return customUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getDetails().getAuthorities();
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        getDetails().setAuthorities(authorities);
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = true;
    }

    /**
     * not used
     */

    @Override
    public Object getPrincipal() {
        return null;
    }


    @Override
    public String getName() {
        return null;
    }
}
