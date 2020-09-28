package kr.pe.ryudaewan.stiletto.member.controller;

import kr.pe.ryudaewan.stiletto.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class AuthenticationToken {
    private Member member;
    private String token;
    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

    public AuthenticationToken(Member member, String token, Collection<? extends GrantedAuthority> authorities) {
        this.member = member;
        this.authorities = authorities;
        this.token = token;
    }

    public Long getMemberId() {
        return this.member.getId();
    }

    public String getScreenName() {
        return this.member.getScreenName();
    }

    public String getEmail() { return this.member.getEmail(); }

    public String getToken() {
        return this.token;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
