package kr.pe.ryudaewan.stiletto.member.service;

import kr.pe.ryudaewan.stiletto.member.DuplicateMemberException;
import kr.pe.ryudaewan.stiletto.member.NoMembersFoundException;
import kr.pe.ryudaewan.stiletto.member.entity.Member;
import kr.pe.ryudaewan.stiletto.member.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

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
}
