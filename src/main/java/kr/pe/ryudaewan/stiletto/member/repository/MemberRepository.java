package kr.pe.ryudaewan.stiletto.member.repository;

import kr.pe.ryudaewan.stiletto.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByScreenName(String screenName, Pageable pageable);

    Page<Member> findByScreenNameStartsWith(String screenName, Pageable pageable);

    Page<Member> findByScreenNameContains(String screenName, Pageable pageable);

    Optional<Member> findByEmailAndPassword(String email, String password);
}
