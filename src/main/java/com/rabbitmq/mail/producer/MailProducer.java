package com.rabbitmq.mail.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailProducer {
    @Value("${spring.mail.username}")
    private String from;

    public SimpleMailMessage generateMail(String subject, String to, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom(from);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        return mailMessage;
    }
}
