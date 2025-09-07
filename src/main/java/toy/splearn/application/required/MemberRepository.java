package toy.splearn.application.required;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import toy.splearn.domain.Email;
import toy.splearn.domain.Member;

import java.util.Optional;

/**
 * 회원 정보를 저장하거나 조회한다
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member save(Member member);

    Optional<Member> findByEmail(Email email);
}
