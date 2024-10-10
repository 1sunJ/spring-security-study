package study.springsecurity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.springsecurity.auth.domain.dto.LoginReq;
import study.springsecurity.auth.domain.dto.JwtTokenDto;
import study.springsecurity.auth.domain.dto.SignUpReq;
import study.springsecurity.auth.kakaoauth.KakaoAuthManager;
import study.springsecurity.auth.kakaoauth.KakaoAuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KakaoAuthManager kakaoAuthManager;
    private final KakaoAuthService kakaoAuthService;

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }

    @GetMapping("/login/kakao")
    public LoginRes login(@RequestParam(name = "code") String code) throws Exception {
        log.info("kakao auth controller start : {}", code);

        String token = kakaoAuthManager.getKakaoToken(code);
        log.info("kakao token : {}", token);

        Long kakaoId = kakaoAuthManager.getKakaoId(token);
        log.info("kakaoId : {}", kakaoId);

        log.info("kakao auth controller end");
        return kakaoAuthService.login(kakaoId);
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignUpReq signUpReq) {
        authService.signUp(signUpReq);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody JwtTokenDto jwtTokenDto) {
        authService.logout(jwtTokenDto);
    }

}
