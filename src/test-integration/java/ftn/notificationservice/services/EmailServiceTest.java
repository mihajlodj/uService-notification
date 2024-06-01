package ftn.notificationservice.services;

import ftn.notificationservice.AuthPostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class EmailServiceTest extends AuthPostgresIntegrationTest {

    @Autowired
    private EmailService emailService;
    @MockBean
    private JavaMailSender mailSender;

    @Test
    public void testMailSend() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.sendMessage("vuk.vukovic.2000@gmail.com", "Test subject", "Test text");

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

}
