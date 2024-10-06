package study.springsecurity.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import study.springsecurity.security.domain.CustomUserDetails;
import study.springsecurity.security.domain.JwtAuthentication;
import study.springsecurity.security.manager.JwtManager;
import study.springsecurity.security.service.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtManager jwtManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String jwtToken = ((JwtAuthentication) authentication).getCredentials();

        jwtManager.validateToken(jwtToken);

        String email = jwtManager.getEmail(jwtToken);
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        return new JwtAuthentication(jwtToken, userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }

}
