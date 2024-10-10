package study.springsecurity.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    @Value("${kakao.location}")
    private String location;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login/page/kakao")
    public String authPage(Model model) {
        model.addAttribute("location", getWholeUri());
        return "login-page";
    }

    private String getWholeUri() {
        return location + "?"
                + "client_id=" + clientId + "&"
                + "redirect_uri=" + redirectUri + "&"
                + "response_type=code";
    }

}
