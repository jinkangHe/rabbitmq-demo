package com.rabbitmq.mail.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.mail.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MailReceiver {
    private static final Logger logger= LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private MailSender mailSender;

    //@RabbitListener(queues = RabbitMQConfig.MAIL_QUEUE)
    public void handler(Message message, Channel channel){
        SimpleMailMessage mailMessage = (SimpleMailMessage) message.getPayload();
        logger.error("收到%s邮件",mailMessage.getSubject());
        mailSender.send(mailMessage);
        logger.warn("发送%s邮件",mailMessage.getSubject());
    }
}
