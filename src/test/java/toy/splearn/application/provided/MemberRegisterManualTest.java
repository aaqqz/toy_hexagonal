package toy.splearn.application.provided;

import toy.splearn.application.required.EmailSender;
import toy.splearn.domain.Email;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static toy.splearn.domain.MemberFixture.createMemberRegisterRequest;

// 여러가지 테스트 방법 (stub, mock, mockito) 참고용 (MemberRegisterTest 실전은 여기)
class MemberRegisterManualTest {

//    @Test
//    void registerTestStub() {
//        MemberRegister register = new MemberService(
//                new MemberRepositoryStub(),
//                new EmailSenderStub(),
//                createPasswordEncoder()
//        );
//
//        Member member = register.register(createMemberRegisterRequest());
//
//        assertThat(member.getId()).isNotNull();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//    }
//
//    @Test
//    void registerTestMock() {
//        EmailSenderMock emailSenderMock = new EmailSenderMock();
//        MemberRegister register = new MemberService(
//                new MemberRepositoryStub(),
//                emailSenderMock,
//                createPasswordEncoder()
//        );
//
//        Member member = register.register(createMemberRegisterRequest());
//
//        assertThat(member.getId()).isNotNull();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//
//        assertThat(emailSenderMock.getTos()).hasSize(1);
//        assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());
//    }

//    static class MemberRepositoryStub implements MemberRepository {
//
//        @Override
//        public Member save(Member member) {
//            ReflectionTestUtils.setField(member, "id", 1L);
//            return member;
//        }
//
//        @Override
//        public Optional<Member> findByEmail(Email email) {
//            return Optional.empty();
//        }
//    }

    static class EmailSenderStub implements EmailSender {

        @Override
        public void send(Email email, String subject, String body) {
        }
    }

    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }

        public List<Email> getTos() {
            return this.tos;
        }
    }
}