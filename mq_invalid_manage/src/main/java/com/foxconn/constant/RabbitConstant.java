package com.foxconn.constant;

/**
 * RabbitMQ配置常量
 */
public class RabbitConstant {


    /**
     * 无效异常消息队列名
     */
    public static final String MQ_INVALID_QUEUE = "mq-invalid-queue";

    /**
     * 无效异常消息队列交换机
     */
    public static final String MQ_INVALID_EXCHANGE = "mq-invalid-exchange";

    /**
     * 无效异常消息队列路由键
     */
    public static final String MQ_INVALID_ROUTING_KEY = "mq-invalid-routingKey";
    
    /**
     * 无效异常消息队列延迟缓冲队列名
     */
    public static final String MQ_INVALID_DELAY_QUEUE = "mq-invalid_delay_queue";
    
    /**
     * 无效异常消息队列延迟缓冲交换机
     */
    public static final String MQ_INVALID_DELAY_EXCHANGE = "mq-invalid_delay_routingKey";

    /**
     * 无效异常消息队列延迟缓冲路由键
     */
    public static final String MQ_INVALID_DELAY_ROUTING_KEY = "mq-invalid_delay_routingKey";

    /**
     * 无效异常消息队列延迟缓时间
     */
    public static final Object MQ_INVALID_DELAY_TTL = 5 * 1000;
}
