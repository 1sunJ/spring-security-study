package study.springsecurity.auth.kakaoauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import study.springsecurity.auth.domain.dto.JwtTokenDto;
import study.springsecurity.member.Member;
import study.springsecurity.member.MemberRepository;
import study.springsecurity.security.domain.enums.TokenType;
import study.springsecurity.security.manager.JwtManager;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

    public JwtTokenDto login(Long kakaoId) {
        Member member = memberRepository.findByKakaoId(kakaoId).orElse(null);

        if (member == null) {
            member = signUp(kakaoId);
        }

        String accessToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), TokenType.ACCESS_TOKEN);
        String refreshToken = jwtManager.generateToken(member.getAuthorities(), member.getId(), TokenType.REFRESH_TOKEN);

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member signUp(Long kakaoId) {
        Member newMember = Member.createByKakao(kakaoId);
        newMember.addAuthority(new SimpleGrantedAuthority("ROLE_NORMAL"));
        memberRepository.save(newMember);
        return newMember;
    }

}
