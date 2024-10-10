package study.springsecurity.test;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.springsecurity.member.Member;
import study.springsecurity.member.MemberRepository;
import study.springsecurity.security.domain.CustomUserDetails;
import study.springsecurity.security.domain.JwtAuthentication;
import study.springsecurity.security.domain.enums.TokenType;
import study.springsecurity.security.manager.JwtManager;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final MemberRepository memberRepository;

    private final JwtManager jwtManager;

    @GetMapping
    public String test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return CustomUserDetails.getEmail(userDetails, false);
    }

    @GetMapping("/token")
    public String getTokenForTest(@RequestParam(name = "userId") Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return jwtManager.generateToken(member.getAuthorities(), member.getId(), TokenType.ACCESS_TOKEN);
    }

}
