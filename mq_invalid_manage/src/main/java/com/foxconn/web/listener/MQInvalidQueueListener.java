package com.foxconn.web.listener;

import com.alibaba.fastjson.JSONObject;
import com.foxconn.common.utils.DingTalkUtil;
import com.foxconn.constant.RabbitConstant;
import com.foxconn.entity.QueueInvalidMsgDo;
import com.foxconn.repo.queue.QueueInvalidMsgMapper;
import com.foxconn.service.rabbitmq.MQInvalidMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常失效消息队列监听
 * @Author: 王俊辉
 * @Date: 2019/12/5 10:26 上午
 */
@Component
@Slf4j
public class MQInvalidQueueListener {

    @Autowired
    QueueInvalidMsgMapper queueInvalidMsgMapper;

    @Autowired
    MQInvalidMessageService MQInvalidMessageService;

    /**
     * 异常失效消息队列监听
     * @Author: 王俊辉
     * @Date: 2019/12/5 10:24 上午
     * @Param: [channel, message]
     * @Return: void
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConstant.MQ_INVALID_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitConstant.MQ_INVALID_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = RabbitConstant.MQ_INVALID_ROUTING_KEY))
    public void MQInvalidQueueListener(Channel channel, Message message) throws IOException {
        String messageString = new String(message.getBody());
        log.info("接受异常消息：[{}]", messageString);
        try {
            QueueInvalidMsgDo queueInvalidMsgDo = JSONObject.parseObject(messageString, QueueInvalidMsgDo.class);
            queueInvalidMsgMapper.insert(queueInvalidMsgDo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            DingTalkUtil.sendToMQInvalidGroup("异常报警->监听异常消息时出错：" + stringWriter);
            e.printStackTrace();
            //拒绝消息
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //发送到延迟队列，等待修复BUG
            /*rabbitMQService.sendRebackQueue(RabbitConstant.MQ_INVALID_DELAY_EXCHANGE,
                    RabbitConstant.MQ_INVALID_DELAY_ROUTING_KEY, messageString);*/
        }
    }

}
