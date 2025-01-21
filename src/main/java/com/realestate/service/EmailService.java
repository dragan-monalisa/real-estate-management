package com.realestate.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Async
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void send(String to, String emailBody) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setFrom("no-reply@homestor.com");
            helper.setTo(to);
            helper.setSubject("no-reply: homestor");
            helper.setText(emailBody, true);

            mailSender.send(mimeMessage);

            log.info("Email sent to: {}", to);

        } catch (Exception e) {
            log.error("Failed to send email due to technical errors: {}", e.getMessage());
        }
    }

}
