package study.springsecurity.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import study.springsecurity.auth.exception.BannedJwtTokenException;
import study.springsecurity.auth.repository.BannedTokenRepository;
import study.springsecurity.security.domain.CustomUserDetails;
import study.springsecurity.security.domain.JwtAuthentication;
import study.springsecurity.security.manager.JwtManager;
import study.springsecurity.security.service.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtManager jwtManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final BannedTokenRepository bannedTokenRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String jwtToken = ((JwtAuthentication) authentication).getCredentials();

        if (bannedTokenRepository.findByToken(jwtToken).isPresent()) {
            throw new BannedJwtTokenException();
        }

        jwtManager.validateToken(jwtToken);

        Long memberId = jwtManager.getMemberId(jwtToken);
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId.toString());

        return new JwtAuthentication(jwtToken, userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }

}
