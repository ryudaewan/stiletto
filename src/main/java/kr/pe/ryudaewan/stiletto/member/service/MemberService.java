package kr.pe.ryudaewan.stiletto.member.service;

import kr.pe.ryudaewan.stiletto.member.DuplicateMemberException;
import kr.pe.ryudaewan.stiletto.member.NoMembersFoundException;
import kr.pe.ryudaewan.stiletto.member.entity.Member;
import kr.pe.ryudaewan.stiletto.member.entity.Role;
import kr.pe.ryudaewan.stiletto.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member createMember(Member memberInfo) {
        Member newMember = null;

        try {
            newMember = this.memberRepository.save(memberInfo);
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicateMemberException(dive, memberInfo.getEmail());
        }

        return newMember;
    }

    @Transactional
    public Member updateMember(Member member) {
        return this.memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        this.memberRepository.deleteById(id);
    }

    public Member findMember(Long id) {
        return this.memberRepository.findById(id)
                .orElseThrow(NoMembersFoundException::new);
    }

    public List<Member> getAllMembers(Pageable pageable) {

        Page<Member> members = this.memberRepository.findAll(pageable);

        if (members.isEmpty()) throw new NoMembersFoundException();

        return members.getContent();
    }

    public List<Member> searchMembers(String screenName, Pageable pageable) {
        Page<Member> selectedMembers = this.memberRepository.findByScreenName(screenName, pageable);

        if (selectedMembers.isEmpty() == false) return selectedMembers.getContent();

        selectedMembers = this.memberRepository.findByScreenNameStartsWith(screenName, pageable);

        if (selectedMembers.isEmpty() == false) return selectedMembers.getContent();

        selectedMembers = this.memberRepository.findByScreenNameContains(screenName, pageable);

        if (selectedMembers.isEmpty() == true) throw new NoMembersFoundException();

        return selectedMembers.getContent();
    }

    public Member signIn(String email, String password) {
        return this.memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(NoMembersFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoMembersFoundException::new);

        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = null;

        if (("admin@example.com").equals(email)) {
            authority = new SimpleGrantedAuthority(Role.ADMIN.getValue());
        } else {
            authority = new SimpleGrantedAuthority(Role.MEMBER.getValue());
        }

        authorities.add(authority);

        //TODO: 일단 구현하지만 회원 생성 시 비밀번호는 DB에 암호문으로 저장하도록 수정 필요.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(member.getPassword());
        this.logger.debug("암호화한 패스워드=[{}]", encryptedPassword);

        return new User(member.getEmail(), encryptedPassword, authorities);
    }
}
