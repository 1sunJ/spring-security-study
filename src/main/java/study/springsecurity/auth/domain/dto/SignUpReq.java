package study.springsecurity.auth.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.springsecurity.member.Member;

@Data
@NoArgsConstructor
public class SignUpReq {

    private String email;
    private String name;
    private String password;

    public Member toEntity() {
        return new Member(name, email, password);
    }

}
