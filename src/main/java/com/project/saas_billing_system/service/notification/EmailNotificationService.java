package com.project.saas_billing_system.service.notification;

import com.project.saas_billing_system.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:}")
    private String fromAddress;

    public void sendNotification(NotificationEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        if (fromAddress != null && !fromAddress.isBlank()) {
            message.setFrom(fromAddress);
        }
        message.setTo(event.getRecipientEmail());
        message.setSubject(event.getSubject());
        message.setText(event.getBody());
        mailSender.send(message);
    }

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        if (fromAddress != null && !fromAddress.isBlank()) {
            message.setFrom(fromAddress);
        }
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
