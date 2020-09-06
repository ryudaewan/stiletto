package kr.pe.ryudaewan.stiletto.member.repository;

import kr.pe.ryudaewan.stiletto.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testCreate() {
        Member member = new Member("Savannah Smith", "savannah.smith@aoe2.co.kr", "k5r4398dkjf%");
        Member newMember = this.memberRepository.save(member);
        assertNotNull(newMember);
        assertNotNull(newMember.getId());
        this.logger.debug("New User Id No. = [{}]", newMember.getId());
    }

    @Test
    public void testCreateDup() {
        Member member1 = new Member("Savannah Smith", "savannah.smith@aoe2.co.kr", "k5r4398dkjf%");
        Member member2 = new Member("Savannah Smith", "savannah.smith@aoe2.co.kr", "dkfjdkjf");
        Member newMember1 = this.memberRepository.save(member1);
        assertNotNull(newMember1);
        assertNotNull(newMember1.getId());
        assertThrows(DataIntegrityViolationException.class, () -> {
            this.memberRepository.save(member2);
        });
    }

    @Test
    public void testFindByIdAndUpdate() {
        long id = 1l;
        String newPassword = "rkerie98ge";
        Optional<Member> user = this.memberRepository.findById(id);

        user.ifPresentOrElse(selectedUser -> {
                    assertEquals(id, selectedUser.getId());
                    selectedUser.setPassword(newPassword);
                    Member updatedMember = memberRepository.save(selectedUser);
                    assertEquals(id, updatedMember.getId());
                    assertEquals(newPassword, updatedMember.getPassword());
                },
                () -> fail());
    }

    @Test
    public void testMemberNotFound() {
        long id = 111111l;
        Optional<Member> user = this.memberRepository.findById(id);

        if (user.isPresent()) fail();
    }
}
