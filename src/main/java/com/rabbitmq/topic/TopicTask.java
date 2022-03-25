package com.rabbitmq.topic;

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
public class TopicTask {
    private static final Logger logger = LoggerFactory.getLogger(TopicTask.class);
    private static Integer index = 0;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "*/50 * * * * ?")
    public void topicTaskProducer() {
        //实验组
        logger.info("TopicTaskProducer发送消息: {}", index);
        //queueCInTopicExchange 设置了 routingKey为"summer.#" 所以可以收到下面两条
        rabbitTemplate.convertAndSend("topicExchange","summer.a",  "topic类型 summer.a" + index);
        rabbitTemplate.convertAndSend("topicExchange","summer.a.b",  "topic类型 summer.a.b" + index);

        //queueDInTopicExchange 设置了 routingKey为"autumn.*" 所以只能收到第一条
        rabbitTemplate.convertAndSend("topicExchange","autumn.a",  "topic类型 autumn.a" + index);
        rabbitTemplate.convertAndSend("topicExchange","autumn.a.b",  "topic类型 autumn.a.b" + index);

        index++;
    }

    //指定监听队列
    @RabbitListener(queues = {"queueCInTopicExchange"})
    public void consumerForQueueCInFanoutExchange(Message message, Channel channel) {
        logger.info("consumerForQueueCInFanoutExchange收到消息：{}", new String(message.getBody()));
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
    @RabbitListener(queues = {"queueDInTopicExchange"})
    public void consumerForQueueDInFanoutExchange(Message message, Channel channel) {
        logger.info("consumerForQueueDInFanoutExchange收到消息：{}", new String(message.getBody()));
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
