package toy.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProfileTest {

    @Test
    void profile() {
        new Profile("test");
        new Profile("test100");
        new Profile("12345");
        new Profile("");
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile("UPPER")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("한글")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("toolongtoolongtoolongtoolong")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("urltest");

        assertThat(profile.url()).isEqualTo("@urltest");
    }
}