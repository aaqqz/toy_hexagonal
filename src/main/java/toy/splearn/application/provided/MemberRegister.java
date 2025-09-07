package toy.splearn.application.provided;

import toy.splearn.domain.Member;
import toy.splearn.domain.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다
 */
public interface MemberRegister {

    Member register(MemberRegisterRequest registerRequest);
}
