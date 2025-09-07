package toy.splearn;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import toy.splearn.application.required.EmailSender;
import toy.splearn.domain.Email;
import toy.splearn.domain.MemberFixture;
import toy.splearn.domain.PasswordEncoder;

@TestConfiguration
public class SplearnTestConfiguration {

    @Primary
    @Bean
    public EmailSender emailSEnder() {
        return new EmailSender() {
            @Override
            public void send(Email email, String subject, String body) {
                System.out.println("sending email: " + email);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
