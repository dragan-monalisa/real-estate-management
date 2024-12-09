package com.realestatemanagement.service;

import com.realestatemanagement.exception.TechnicalException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String emailBody) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom("no-reply@homestor.com");
            helper.setTo(to);
            helper.setSubject("no-reply: homestor");
            helper.setText(emailBody, true);

            mailSender.send(mimeMessage);

        } catch(Exception e) {
            throw new TechnicalException(e.getMessage());
        }
    }
}
