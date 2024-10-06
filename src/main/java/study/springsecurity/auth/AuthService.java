package study.springsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springsecurity.auth.domain.dto.LoginReq;
import study.springsecurity.auth.domain.dto.LoginRes;
import study.springsecurity.auth.exception.NotMatchedLoginInfo;
import study.springsecurity.member.Member;
import study.springsecurity.member.MemberRepository;
import study.springsecurity.security.domain.enums.TokenType;
import study.springsecurity.security.manager.JwtManager;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

    public LoginRes login(LoginReq loginReq) {
        Member member = memberRepository.findByEmail(loginReq.getEmail()).orElseThrow(NotMatchedLoginInfo::new);

        if (!member.getPassword().equals(loginReq.getPassword())) {
            throw new NotMatchedLoginInfo();
        }

        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getEmail(), TokenType.ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getEmail(), TokenType.REFRESH_TOKEN);


        return LoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
