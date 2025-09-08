package toy.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import toy.splearn.SplearnTestConfiguration;
import toy.splearn.domain.member.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    // @Transactional 으로 대체 rollback 된다
//    @BeforeEach
//    void setUp() {
//        memberRepository.deleteAllInBatch();
//    }

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = registerMember();
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("splearn", "12345", "1234567890"));
        checkValidation(new MemberRegisterRequest("splearn@gmail.com", "1234", "1234567890"));
        checkValidation(new MemberRegisterRequest("splearn@gmail.com", "12345", "1234567"));
    }

    @Test
    void deactivate() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        Member member = registerMember();
        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        var request = new MemberInfoUpdateRequest("updatedNickname", "updatedprofile", "자기소개");
        memberRegister.updateInfo(member.getId(), request);

        checkValidation(new MemberRegisterRequest("splearn", "12345", "1234567890"));
        checkValidation(new MemberRegisterRequest("splearn@gmail.com", "1234", "1234567890"));
        checkValidation(new MemberRegisterRequest("splearn@gmail.com", "12345", "1234567"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void updateInfoFail() {
        Member member1 = registerMember();
        memberRegister.activate(member1.getId());
        memberRegister.updateInfo(member1.getId(), new MemberInfoUpdateRequest("Peter", "profile100", "자기소개"));

        Member member2 = registerMember("splearn2@email.com");
        memberRegister.activate(member2.getId());
        entityManager.flush();
        entityManager.clear();

        // member2 > 기존의 member와 같은 프로필 주소를 사용할 수 없다
        assertThatThrownBy(() -> {
            memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("James", "profile100", "Introduction"));
        }).isInstanceOf(DuplicateProfileException.class);

        // member2 > 다른 프로필 주소로는 변경 가능
        memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("James", "profile200", "Introduction"));

        // 기존 프로필 주소를 바꾸는 것도 가능
        memberRegister.updateInfo(member1.getId(), new MemberInfoUpdateRequest("James", "profile100", "Introduction"));

        // 프로필 주소를 제거하는 것도 가능
        memberRegister.updateInfo(member1.getId(), new MemberInfoUpdateRequest("James", "", "Introduction"));

        // 프로필 주소 중복는 허용하지 않음
        assertThatThrownBy(() -> {
            memberRegister.updateInfo(member1.getId(), new MemberInfoUpdateRequest("James", "profile200", "Introduction"));
        }).isInstanceOf(DuplicateProfileException.class);

    }

    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    private Member registerMember(String email) {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
        entityManager.flush();
        entityManager.clear();
        return member;
    }
}