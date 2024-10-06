package study.springsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.springsecurity.auth.domain.dto.LoginReq;
import study.springsecurity.auth.domain.dto.LoginRes;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }

}
