package kr.pe.ryudaewan.stiletto.member.service;

import kr.pe.ryudaewan.stiletto.member.NoMembersFoundException;
import kr.pe.ryudaewan.stiletto.member.entity.Member;
import kr.pe.ryudaewan.stiletto.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member createMember(Member memberInfo) {

        return this.memberRepository.save(memberInfo);
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
        List<Member> memberList = new ArrayList<>();
        Page<Member> members = this.memberRepository.findAll(pageable);

        if (members.isEmpty()) throw new NoMembersFoundException();

        members.forEach(memberList::add);

        return memberList;
    }

    public List<Member> searchMembers(String screenName, Pageable pageable) {
        List<Member> members = this.memberRepository.findByScreenName(screenName, pageable);

        if (null != members && members.size() > 0) {
            return members;
        }

        members = this.memberRepository.findByScreenNameStartsWith(screenName, pageable);

        if (null != members && members.size() > 0) {
            return members;
        }

        members = this.memberRepository.findByScreenNameContains(screenName, pageable);

        if (null == members || members.size() < 1) {
            throw new NoMembersFoundException();
        }

        return members;
    }
}
