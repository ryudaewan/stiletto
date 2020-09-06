package kr.pe.ryudaewan.stiletto.member.controller;

import kr.pe.ryudaewan.stiletto.member.NoMembersFoundException;
import kr.pe.ryudaewan.stiletto.member.entity.Member;
import kr.pe.ryudaewan.stiletto.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignInController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public Member signIn(@RequestBody Member credential) {

        String email = credential.getEmail();
        String password = credential.getPassword();

        logger.debug("email=[{}], password=[{}]", email, password);
        Member memberFound = this.memberService.signIn(email, password);

        // TODO: HTTP Session을 생성시키는 로직 추가

        return memberFound;
    }

    @ExceptionHandler(NoMembersFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void signInFailed(NoMembersFoundException nmfe) {
        this.logger.debug("로그인 실패");
    }
}
