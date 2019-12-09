package com.foxconn.service.rabbitmq;

import com.foxconn.common.mo.CorrelationDataMO;

/**
 * rabbitMQ服务提供
 * @Author: 王俊辉
 * @Date: 2019/12/6 10:15 上午
 */
public interface MQInvalidMessageService {

    /**
     * 消息重发操作
     * @Author: 王俊辉
     * @Date: 2019/12/7 9:41 上午
     * @Param: [exchange, routingKey, data, correlationDataMO]
     * @Return: void
     */
    void sendRebackQueue(Integer id);
}
