package study.springsecurity.test;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.springsecurity.member.Member;
import study.springsecurity.member.MemberRepository;

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataSetter {

    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initTestData() {
        Member member1 = new Member("1sunJ", "won9619v@naver.com", "1234");
        member1.addAuthority(new SimpleGrantedAuthority("ROLE_NORMAL"));
        Member member2 = new Member("2hunA", "abcdefg@naver.com", "1234");
        member2.addAuthority(new SimpleGrantedAuthority("ROLE_NORMAL"));
        memberRepository.save(member1);
        memberRepository.save(member2);
    }

}
