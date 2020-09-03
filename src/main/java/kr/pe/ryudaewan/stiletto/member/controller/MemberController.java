package kr.pe.ryudaewan.stiletto.member.controller;

import kr.pe.ryudaewan.stiletto.member.NoMembersFoundException;
import kr.pe.ryudaewan.stiletto.member.entity.Member;
import kr.pe.ryudaewan.stiletto.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
//@RequestMapping(value = "/member", produces = "application/json;charset=UTF-8")
@RequestMapping(value = "/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createMember(@RequestBody Member memberInfo) {

        Member newMember = this.memberService.createMember(memberInfo);
        long id = newMember.getId();
        URI createdURI = linkTo(methodOn(this.getClass()).findMember(id)).toUri();
        return ResponseEntity.created(createdURI).body(newMember);
    }

    @PutMapping("/update")
    public Member updateMember(@RequestBody Member member) {
        return this.memberService.updateMember(member);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMember(@PathVariable Long id) {
        this.memberService.deleteMember(id);
    }

    @GetMapping("/find/{id}")
    public Member findMember(@PathVariable Long id) {
        return this.memberService.findMember(id);
    }

    @GetMapping("/list")
    public List<Member> getAllMembers(@PageableDefault Pageable pageable) {
        return this.memberService.getAllMembers(pageable);
    }

    @GetMapping("/search/{screenName}")
    public List<Member> searchMembers(@PageableDefault Pageable pageable, @PathVariable String screenName) {

        return this.memberService.searchMembers(screenName, pageable);
    }

    @ExceptionHandler(NoMembersFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Member noMembersFound(NoMembersFoundException nmfe) {
        return null;
    }
}
