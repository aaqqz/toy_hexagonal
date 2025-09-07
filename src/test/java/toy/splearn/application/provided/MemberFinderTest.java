package toy.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import toy.splearn.SplearnTestConfiguration;
import toy.splearn.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static toy.splearn.domain.MemberFixture.createMemberRegisterRequest;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberFinderTest {

    private final MemberFinder memberFinder;
    private final MemberRegister memberRegister;
    private final EntityManager entityManager;

    @Test
    void find() {
        Member member = memberRegister.register(createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member found = memberFinder.find(member.getId());
        
        assertThat(member.getId()).isEqualTo(found.getId());
    }
    
    @Test
    void findFail() {
        Assertions.assertThatThrownBy(() -> memberFinder.find(9999L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}