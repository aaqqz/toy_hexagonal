package toy.splearn.adaptor.integration;

import org.springframework.stereotype.Component;
import toy.splearn.application.member.required.EmailSender;
import toy.splearn.domain.shared.Email;

@Component
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender send email: " + email);
    }
}
