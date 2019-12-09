package com.foxconn.service.controller;

import com.foxconn.common.mo.PageDataMO;
import com.foxconn.web.mo.req.QueueInvalidMsgListMo;

import java.util.List;

/**
 * 队列失效消息控制层服务
 * @Author: 王俊辉
 * @Date: 2019/12/5 11:27 上午
 */
public interface QueueInvalidMsgService {

    /**
     * 获取队列失效消息列表
     * @Author: 王俊辉
     * @Date: 2019/12/5 11:33 上午
     * @Param: [queueInvalidMsgListMo]
     * @Return: java.util.List<com.foxconn.entity.QueueInvalidMsgDo>
     */
    PageDataMO getQueueInvalidMsgList(QueueInvalidMsgListMo queueInvalidMsgListMo);

    /**
     * 失效队列消息失效操作
     * @Author: 王俊辉
     * @Date: 2019/12/5 6:00 下午
     * @Param: [id]
     * @Return: void
     */
    void invalidRecord(Integer id);

    /**
     * 失效队列消息重发操作
     * @Author: 王俊辉
     * @Date: 2019/12/6 2:52 下午
     * @Param: [id]
     * @Return: void
     */
    void rebackQueueById(Integer id);

    /**
     * 获取队列名列表
     * @Author: 王俊辉
     * @Date: 2019/12/7 10:42 上午
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     */
    List<String> getQueueNames();
}
