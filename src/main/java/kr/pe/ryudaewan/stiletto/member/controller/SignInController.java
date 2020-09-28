package kr.pe.ryudaewan.stiletto.member.controller;

import kr.pe.ryudaewan.stiletto.member.NoMembersFoundException;
import kr.pe.ryudaewan.stiletto.member.entity.Member;
import kr.pe.ryudaewan.stiletto.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class SignInController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private MemberService memberService;

    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationToken signIn(@RequestBody SignInRequest signInRequest, HttpSession session) {

        String email = signInRequest.getEmail(); // principal
        String password = signInRequest.getPassword(); // crediential

        logger.debug("email=[{}], password=[{}]", email, password);
        Member memberFound = this.memberService.signIn(email, password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(token);
        this.logger.debug("Authentication=[{}]", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return new AuthenticationToken(memberFound, session.getId(), authentication.getAuthorities());
    }

    @ExceptionHandler(NoMembersFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void signInFailed(NoMembersFoundException nmfe) {
        this.logger.debug("로그인 실패");
    }


}
