package kr.pe.ryudaewan.stiletto.member;

import kr.pe.ryudaewan.stiletto.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void create() {
        /*User user = new User();
        user.setEmail("user1@gmail.com");
        User newUser = userRepository.save(user);
        System.out.println(newUser); */

    }

    @Test
    public void update() {
/*        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(selectedUser -> {
            selectedUser.setEmail("modUser1@gmail.com");
            User newUser = userRepository.save(selectedUser);
            System.out.println("user: "+ newUser);
        }); */
    }
}
