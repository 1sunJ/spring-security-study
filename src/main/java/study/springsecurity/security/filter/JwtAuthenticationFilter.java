package study.springsecurity.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import study.springsecurity.member.MemberRepository;
import study.springsecurity.security.domain.JwtAuthentication;
import study.springsecurity.security.manager.JwtManager;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("========== JwtFilter start ==========");

        String token = jwtManager.extractToken(request);
        log.info("token : {}", token);

        JwtAuthentication jwtAuthentication = new JwtAuthentication(token);
        authenticationManager.authenticate(jwtAuthentication);

        log.info("========== JwtFilter end ==========");
        filterChain.doFilter(request, response);
    }

    /**
     * 기존 로직
     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("========== JwtFilter start ==========");
//
//        String token = jwtManager.extractToken(request);
//        log.info("token : {}", token);
//
//        if (token != null) {
//            jwtManager.validateToken(token);
//
//            String email = jwtManager.getEmail(token);
//            Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
//            log.info("email : {}", email);
//
//            UserDetails userDetails = new CustomUserDetails(member);
//            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", jwtManager.getAuthorities(token));
//            authentication.setAuthenticated(true);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else {
//            log.info("no token request");
//        }
//
//        log.info("========== JwtFilter end ==========");
//        filterChain.doFilter(request, response);
//    }

}
