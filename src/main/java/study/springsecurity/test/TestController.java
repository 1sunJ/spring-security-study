package study.springsecurity.test;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.springsecurity.member.Member;
import study.springsecurity.member.MemberRepository;
import study.springsecurity.security.domain.CustomUserDetails;
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

    @GetMapping("/token")
    public String getTokenForTest(@RequestParam(name = "userId") Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, "", member.getAuthorities());
        return jwtManager.generateToken(authentication, TokenType.ACCESS_TOKEN);
    }

}
