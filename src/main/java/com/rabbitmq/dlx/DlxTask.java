package com.rabbitmq.dlx;

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
 * @date 2022/3/23 14:20
 */
@Component
public class DlxTask {
    private static final Logger logger = LoggerFactory.getLogger(DlxTask.class);
    private static Integer index = 0;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "*/5 * * * * ?")
    public void producer() {
        //实验组
        logger.info("DlxTaskTask发送消息: {}", index);
        rabbitTemplate.convertAndSend("normalExchange", "normal", "测试死信队列-" + index);
        index++;

    }

    //指定监听队列
    @RabbitListener(queues = {"normalQueue"})
    public void consumerForNormal(Message message, Channel channel) {
        if (Math.random() > 0.45) {
            logger.info("consumerForNormal收到消息：{}", new String(message.getBody()));
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {
            //
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.error("{}消息未消费", new String(message.getBody()));
        }
    }

    //指定监听队列
    @RabbitListener(queues = {"dlxQueue"})
    public void consumerForDlx(Message message, Channel channel) throws IOException {
      
        logger.info("consumerForDlx收到消息：{}", new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);


    }

}
