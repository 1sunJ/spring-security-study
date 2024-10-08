package study.springsecurity.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.springsecurity.auth.domain.BannedToken;

import java.util.Optional;

public interface BannedTokenRepository extends JpaRepository<BannedToken, Long> {

    Optional<BannedToken> findByToken(String token);

}
