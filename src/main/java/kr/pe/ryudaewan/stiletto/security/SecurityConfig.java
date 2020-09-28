package kr.pe.ryudaewan.stiletto.security;

import kr.pe.ryudaewan.stiletto.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    public void configure(WebSecurity web) {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상 통과 )
        web.ignoring().antMatchers("/**/*.css", "/**/*.js", "/**/*.svg");
    }

    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {
        httpSec
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/signIn").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/member").hasAuthority("USER")
                .anyRequest().authenticated()
                /* .and()
                .formLogin()
                .loginPage("/signIn") */
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/signOut", "POST"))
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.memberService)
                .passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpSessionIdResolver httpSessionStrategy() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }
}
