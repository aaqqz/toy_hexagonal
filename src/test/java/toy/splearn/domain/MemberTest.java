package toy.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    void createMember() {
        // given
        var member = initMember();

        // expected
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, "nickname", "secret"))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> new Member("email", null, "secret"))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> new Member("email", "nickname", null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        // given
        var member = new Member("test@email.com", "nickname", "secret");

        // when
        member.activate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        // given
        var member = new Member("test@email.com", "nickname", "secret");

        // when
        member.activate();

        // then
        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        // given
        var member = initMember();
        member.activate();

        // when
        member.deactivate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail_v1() {
        // given
        var member = initMember();

        // expected
        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivateFail_v2() {
        // given
        var member = initMember();
        member.activate();
        member.deactivate();

        // expected
        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }

    private Member initMember() {
        return new Member("test@email.com", "nickname", "secret");
    }
}