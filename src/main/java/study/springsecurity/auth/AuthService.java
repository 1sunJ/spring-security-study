package study.springsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.springsecurity.auth.domain.BannedToken;
import study.springsecurity.auth.domain.dto.LoginReq;
import study.springsecurity.auth.domain.dto.JwtTokenDto;
import study.springsecurity.auth.domain.dto.SignUpReq;
import study.springsecurity.auth.exception.ExisingEmailException;
import study.springsecurity.auth.exception.ExisingNameException;
import study.springsecurity.auth.exception.NotMatchedLoginInfo;
import study.springsecurity.auth.repository.BannedTokenRepository;
import study.springsecurity.member.Member;
import study.springsecurity.member.MemberRepository;
import study.springsecurity.security.domain.enums.TokenType;
import study.springsecurity.security.manager.JwtManager;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final BannedTokenRepository bannedTokenRepository;

    public JwtTokenDto login(LoginReq loginReq) {
        Member member = memberRepository.findByEmail(loginReq.getEmail()).orElseThrow(NotMatchedLoginInfo::new);

        if (!member.getPassword().equals(loginReq.getPassword())) {
            throw new NotMatchedLoginInfo();
        }

        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getEmail(), TokenType.ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getEmail(), TokenType.REFRESH_TOKEN);


        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void signUp(SignUpReq signUpReq) {
        if (memberRepository.existsByEmail(signUpReq.getEmail())) {
            throw new ExisingEmailException();
        }

        if (memberRepository.existsByName(signUpReq.getName())) {
            throw new ExisingNameException();
        }

        Member member = signUpReq.toEntity();
        member.addAuthority(new SimpleGrantedAuthority("ROLE_NORMAL"));
        memberRepository.save(member);
    }

    public void logout(JwtTokenDto jwtTokenDto) {
        Optional<BannedToken> accessOptional = bannedTokenRepository.findByToken(jwtTokenDto.getAccessToken());
        if (accessOptional.isEmpty()) {
            bannedTokenRepository.save(new BannedToken(jwtTokenDto.getAccessToken()));
        }

        Optional<BannedToken> refreshOptional = bannedTokenRepository.findByToken(jwtTokenDto.getRefreshToken());
        if (accessOptional.isEmpty()) {
            bannedTokenRepository.save(new BannedToken(jwtTokenDto.getRefreshToken()));
        }
    }

}
