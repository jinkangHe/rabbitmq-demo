package com.rabbitmq.mail.mailsender;

import com.rabbitmq.mail.consumer.MailReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MailSender {

    private static final Logger logger= LoggerFactory.getLogger(MailSender.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(SimpleMailMessage message) {
        mailSender.send(message);
        logger.debug(message.getSubject() + "邮件已发送");
    }


}
