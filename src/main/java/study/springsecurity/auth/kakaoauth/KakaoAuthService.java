package study.springsecurity.auth.kakaoauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.springsecurity.auth.domain.dto.LoginRes;
import study.springsecurity.member.Member;
import study.springsecurity.member.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final MemberRepository memberRepository;

    public LoginRes login(Long kakaoId) {
        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);

        if (memberOptional.isEmpty()) {
            signUp(kakaoId);
        }

        return null;
    }

    private void signUp(Long kakaoId) {
        Member newMember = Member.createByKakao(kakaoId);
        memberRepository.save(newMember);
    }

}
