package com.foxconn.web.listener;

import com.foxconn.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 无效异常消息队列延迟队列配置
 * @Author: 王俊辉
 * @Date: 2019/12/6 2:00 下午
 */
@Service
public class MQInvalidDelayQueueConfig {

    /**
     * 无效异常消息队列延迟队列配置
     * <p>
     * 1、params.put("x-message-ttl", 5 * 1000);
     * 第一种方式是直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活,（当然二者是兼容的,默认是时间小的优先）
     * 2、rabbitTemplate.convertAndSend(book, message -> {
     * message.getMessageProperties().setExpiration(2 * 1000 + "");
     * return message;
     * });
     * 第二种就是每次发送消息动态设置延迟时间,这样我们可以灵活控制
     **/
    @Bean
    public Queue delayOrderQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", RabbitConstant.MQ_INVALID_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称
        params.put("x-dead-letter-routing-key", RabbitConstant.MQ_INVALID_ROUTING_KEY);
        params.put("x-message-ttl", RabbitConstant.MQ_INVALID_DELAY_TTL);
        return new Queue(RabbitConstant.MQ_INVALID_DELAY_QUEUE, true, false, false, params);
    }
    /**
     * 需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，
     * 不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
     * @return DirectExchange
     */
    @Bean
    public DirectExchange orderDelayExchange() {
        return new DirectExchange(RabbitConstant.MQ_INVALID_DELAY_EXCHANGE);
    }
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(delayOrderQueue()).to(orderDelayExchange()).with(RabbitConstant.MQ_INVALID_DELAY_ROUTING_KEY);
    }

}
