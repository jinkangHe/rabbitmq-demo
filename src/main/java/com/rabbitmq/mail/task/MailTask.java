package com.rabbitmq.mail.task;

import com.rabbitmq.mail.config.RabbitMQConfig;
import com.rabbitmq.mail.producer.MailProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class MailTask {
    private static final Logger logger = LoggerFactory.getLogger(MailTask.class);
    private static final String SUBJECT_PREFIX = "消息队列发送邮件【%s】";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailProducer mailProducer;

    @Scheduled(cron = "*/30 * * * * ?")
    public void sendMail() {
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
        String now = df.format(date);

        SimpleMailMessage mailMessage = mailProducer.generateMail(String.format(SUBJECT_PREFIX, now)
                , "jinkanghe@163.com"
                , "这是一封测试内容》》》" + now);
        rabbitTemplate.convertAndSend(RabbitMQConfig.MAIL_TOPIC_EXCHANGE
                , "mail", mailMessage);
        logger.info("发送邮件" + mailMessage.getSubject());
    }
}
