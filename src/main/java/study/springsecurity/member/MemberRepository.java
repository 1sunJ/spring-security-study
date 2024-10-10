package study.springsecurity.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByKakaoId(Long kakaoId);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String Email);

    boolean existsByName(String name);


}
