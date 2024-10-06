package study.springsecurity.test;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test")
    public String test() {
        return "successful";
    }

    @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"})
    @GetMapping("/test2")
    public String test2() {
        return "successful";
    }

}
