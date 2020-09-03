package kr.pe.ryudaewan.stiletto.member.repository;

import kr.pe.ryudaewan.stiletto.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByScreenName(String screenName, Pageable pageable);

    List<Member> findByScreenNameStartsWith(String screenName, Pageable pageable);

    List<Member> findByScreenNameContains(String screenName, Pageable pageable);
}
