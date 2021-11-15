package com.rabbitmq.mail.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.Message;

//@Component
public class MailReceiver {
    private static final Logger logger= LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private MailSender mailSender;

    //@RabbitListener(queues = RabbitMQConfig.MAIL_QUEUE)
    public void handler(Message message, Channel channel){
        SimpleMailMessage mailMessage = (SimpleMailMessage) message.getPayload();
        logger.error("收到{}邮件",mailMessage.getSubject());
        mailSender.send(mailMessage);
        logger.warn("发送{}邮件",mailMessage.getSubject());
    }


    //@RabbitListener(queues = "fanoutQueenA")
    public void fanoutQueenAHandler(Message message, Channel channel){
        logger.error("收到消息{}",message.getPayload());
    }

    //@RabbitListener(queues = "fanoutQueenB")
    public void fanoutQueenBHandler(Message message, Channel channel){
        logger.error("收到消息{}",message.getPayload());
    }

    //@RabbitListener(queues = "directQueue")
    public void directQueueHandler(Message message, Channel channel){
        logger.error("收到消息{}",message.getPayload());
    }

   // @RabbitListener(queues = "topicQueue#")
    public void topicQueuePoundHandler(Message message, Channel channel){
        logger.error("收到消息{}",message.getPayload());
    }

    //@RabbitListener(queues = "topicQueue*")
    public void topicQueueAsteriskHandler(Message message, Channel channel){
        logger.error("收到消息{}",message.getPayload());
    }
}
