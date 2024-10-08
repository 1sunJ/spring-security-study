package study.springsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.springsecurity.auth.domain.dto.LoginReq;
import study.springsecurity.auth.domain.dto.JwtTokenDto;
import study.springsecurity.auth.domain.dto.SignUpReq;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
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
