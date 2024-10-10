package study.springsecurity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.springsecurity.auth.domain.dto.LoginReq;
import study.springsecurity.auth.domain.dto.LoginRes;
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
    public LoginRes login(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }

    @GetMapping("/login/kakao")
    public LoginRes login(@RequestParam(name = "code") String code) throws Exception {
        log.info("kakao auth controller start : {}", code);

        String token = kakaoAuthManager.getKakaoToken(code);
        log.info("kakao token : {}", token);
//
//        String kakaoId = kakaoAuthService.getKakaoInfo();
//        log.info("kakaoId : {}", kakaoId);

        log.info("kakao auth controller end");

        // signUp 여부 확인하여 처리 후 토큰 반환
//        return kakaoAuthService.login(kakaoId);
        return null;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignUpReq signUpReq) {
        authService.signUp(signUpReq);
    }

}
