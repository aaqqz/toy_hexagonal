package toy.splearn.adaptor.integration;

import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;
import toy.splearn.application.member.required.EmailSender;
import toy.splearn.domain.shared.Email;

@Component
@Fallback
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender send email: " + email);
    }
}
