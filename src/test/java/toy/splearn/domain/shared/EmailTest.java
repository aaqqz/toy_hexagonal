package toy.splearn.domain.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {
    
    @Test
    void equality() {
        var email_1 = new Email("test@email.com");
        var email_2 = new Email("test@email.com");

        assertThat(email_1).isEqualTo(email_2);
    }

}