package com.rabbitmq.headersexchange;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2022/3/24 15:36
 */
@Component
public class HeaderTask {
    private static final Logger logger = LoggerFactory.getLogger(HeaderTask.class);
    private static Integer index = 0;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "*/8 * * * * ?")
    public void FanoutTaskProducer() {
        //实验组
        logger.info("FanoutTaskProducer发送消息: {}", index);
        //routingKey不管写什么都可以
        rabbitTemplate.convertAndSend("fanoutExchange","fanoutExchange",  "广播类型消息" + index);
        index++;
    }

    //指定监听队列
    @RabbitListener(queues = {"queueAInFanoutExchange"})
    public void consumerForQueueAInFanoutExchange(Message message, Channel channel) {
        logger.info("consumerForQueueAInFanoutExchange收到消息：{}", new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //指定监听队列
    @RabbitListener(queues = {"queueBInFanoutExchange"})
    public void consumerForQueueBInFanoutExchange(Message message, Channel channel) {
        logger.info("consumerForQueueBInFanoutExchange收到消息：{}", new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
