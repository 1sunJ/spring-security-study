package study.springsecurity.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.springsecurity.member.Member;
import study.springsecurity.security.domain.CustomUserDetails;
import study.springsecurity.security.domain.enums.TokenType;
import study.springsecurity.security.manager.JwtManager;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class JwtManagerTest {

    private final JwtManager jwtManager;

    @Autowired
    public JwtManagerTest(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Test
    void validationToken() {
        Member member = new Member("1sunJ", "won9619v@naver.com", "1234");
        member.getAuthorities().add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = new CustomUserDetails(member.getEmail(), member.getAuthorities());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", member.getAuthorities());

        String token = jwtManager.generateToken(authentication, TokenType.ACCESS_TOKEN);
        log.info("token 발급 : {}", token);

        jwtManager.validateToken(token);
    }

    @Test
    void generateToken() {
        Member member = new Member("1sunJ", "won9619v@naver.com", "1234");
        member.getAuthorities().add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = new CustomUserDetails(member.getEmail(), member.getAuthorities());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", member.getAuthorities());

        String token = jwtManager.generateToken(authentication, TokenType.ACCESS_TOKEN);
        log.info("token 발급 : {}", token);

        // token 훼손
        token = "DAMAGED " + token;
        log.info("훼손된 token : {}", token);

        jwtManager.validateToken(token);
    }

    @Test
    void getMemberEmail() {
        Member member = new Member("1sunJ", "won9619v@naver.com", "1234");
        member.getAuthorities().add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = new CustomUserDetails(member.getEmail(), member.getAuthorities());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", member.getAuthorities());

        String token = jwtManager.generateToken(authentication, TokenType.ACCESS_TOKEN);
        log.info("token 발급 : {}", token);

        Long memberId = jwtManager.getMemberId(token);
        log.info("email : {}", memberId);

        assertThat(memberId).isEqualTo(member.getId());
    }


}