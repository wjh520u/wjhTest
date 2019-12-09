package com.foxconn.common.mo;

import lombok.Data;
import org.springframework.amqp.rabbit.support.CorrelationData;

import java.util.concurrent.CountDownLatch;
/**
 * 异步消息确认载体
 * @Author: 王俊辉
 * @Date: 2019/12/7 9:36 上午
 */
@Data
public class CorrelationDataMO extends CorrelationData {

    public CorrelationDataMO(CountDownLatch countDownLatch, String messsge){
        this.countDownLatch = countDownLatch;
        this.messsge = messsge;
    }

    private Boolean isSuccess ;

    private String cause;

    private CountDownLatch countDownLatch;

    private String messsge;

}
