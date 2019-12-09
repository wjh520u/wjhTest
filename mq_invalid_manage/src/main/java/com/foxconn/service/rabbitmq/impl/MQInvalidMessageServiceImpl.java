package com.foxconn.service.rabbitmq.impl;

import com.foxconn.common.exception.BaseException;
import com.foxconn.common.mo.CorrelationDataMO;
import com.foxconn.constant.QueueInvalidMsgDoConstants;
import com.foxconn.entity.QueueInvalidMsgDo;
import com.foxconn.repo.queue.QueueInvalidMsgMapper;
import com.foxconn.service.rabbitmq.MQInvalidMessageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * rabbitMQ服务提供
 * @Author: 王俊辉
 * @Date: 2019/12/6 10:15 上午
 */
@Service
@Slf4j
public class MQInvalidMessageServiceImpl implements MQInvalidMessageService,RabbitTemplate.ConfirmCallback , RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    QueueInvalidMsgMapper queueInvalidMsgMapper;

    /**
     * 消息发送失败信息载体
     */
    private static final ThreadLocal<Reply> replyData = new ThreadLocal<>();

    @PostConstruct
    public void initMethod(){
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 消息重发操作
     * @Author: 王俊辉
     * @Date: 2019/12/7 9:41 上午
     * @Param: [exchange, routingKey, data, correlationDataMO]
     * @Return: void
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void sendRebackQueue(Integer id){
        QueueInvalidMsgDo queueInvalidMsgDo = queueInvalidMsgMapper.selectByPrimaryKey(id);
        Integer status = queueInvalidMsgDo.getStatus();
        if(status == null){
            throw new BaseException("该消息状态异常。");
        }
        if (status == QueueInvalidMsgDoConstants.STATUS_IS_REBACK){
            throw new BaseException("该消息不能重复重发。");
        }else if(status == QueueInvalidMsgDoConstants.STATUS_INVALID){
            throw new BaseException("该消息已无效，不能重发。");
        }

        //更新已重发状态
        QueueInvalidMsgDo queueInvalidMsgDoUpdate = new QueueInvalidMsgDo();
        queueInvalidMsgDoUpdate.setId(id);
        queueInvalidMsgDoUpdate.setStatus(QueueInvalidMsgDoConstants.STATUS_IS_REBACK);
        queueInvalidMsgDoUpdate.setUpdatedtime(new Date());
        int offect = queueInvalidMsgMapper.updateByPrimaryKeySelective(queueInvalidMsgDoUpdate);
        if (offect == 0){
            throw new BaseException("重发异常。");
        }

        //重发消息，由于SpringAMPQ发送消息时，是多线程操作，导致本线程不能确认是否真的已经发送，所以需要多线程通讯确认
        CorrelationDataMO correlationDataMO = new CorrelationDataMO(new CountDownLatch(1), queueInvalidMsgDo.getQueueMessage());
        try {
            log.info("发送消息：exchange=" + queueInvalidMsgDo.getExchange() + ",routingKey=" + queueInvalidMsgDo.getQueueRoutingKey() + ",message="
                    + queueInvalidMsgDo.getQueueMessage() );
            rabbitTemplate.convertAndSend(queueInvalidMsgDo.getExchange(),
                    queueInvalidMsgDo.getQueueRoutingKey(),
                    queueInvalidMsgDo.getQueueMessage(),
                    correlationDataMO
            );
            //等待消息发送完成
            correlationDataMO.getCountDownLatch().await(120, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("发送失败。");
        }
        boolean isSuccess = correlationDataMO.getIsSuccess() == null ? false : correlationDataMO.getIsSuccess();
        if (!isSuccess){
            throw new BaseException("发送失败。" + (correlationDataMO.getCause() == null ? "" : " -> " + correlationDataMO.getCause()));
        }
    }

    /**
     * 发送时是否到达交换机回调，本回调一定会执行，但在本类returnedMessage回调之后
     * @Author: 王俊辉
     * @Date: 2019/12/6 6:04 下午
     * @Param: [correlationData, ack, cause]
     * @Return: void
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        CorrelationDataMO correlationDataMO = (CorrelationDataMO )correlationData;
        //获取MQ失败信息，参见本类returnedMessage回调方法
        Reply reply = replyData.get();
        //ack只代表是否发送到达交换机上，而MQ是否接受还得依据本类returnedMessage方法是否有无回调
        if (ack){
            //如果有失败信息，则没发送成功。
            if (reply != null){
                correlationDataMO.setIsSuccess(false);
                correlationDataMO.setCause(reply.getReplyText());
            }else {
                //没有失败信息，则发送成功。
                correlationDataMO.setIsSuccess(true);
            }
        }else {
            correlationDataMO.setIsSuccess(false);
            correlationDataMO.setCause(cause);
        }
        //必须清空失败信息
        replyData.set(null);
        //业务线程唤醒，已经确定消息发送状态
        ((CorrelationDataMO)correlationData).getCountDownLatch().countDown();
        if (!ack ){
            log.info("发送消息异常：" + cause );
        }else if (reply != null){
            log.info("发送消息异常：" + reply.toString());
        }else {
            log.info("发送消息成功：" + correlationDataMO.getMesssge());
        }
    }

    /**
     * 只有当MQ接受失败或拒收，即发送失败时才会回调，一旦回调，在本类confirm回调之前
     * @Author: 王俊辉
     * @Date: 2019/12/7 9:35 上午
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        //设置失败信息，供本类confirm方法判断
        replyData.set(new Reply(replyCode, replyText, exchange, routingKey));
    }

    @Data
    @AllArgsConstructor
    class Reply{
        private int replyCode;
        private String replyText;
        private String exchange;
        private String routingKey;
    }

}
