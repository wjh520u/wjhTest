package com.foxconn.web.controller;

import com.foxconn.common.interfaces.LockTask;
import com.foxconn.common.mo.PageDataMO;
import com.foxconn.common.mo.ResponseMO;
import com.foxconn.common.utils.LockUtil;
import com.foxconn.common.utils.ResponseUtil;
import com.foxconn.constant.LockPrefixConstans;
import com.foxconn.service.controller.QueueInvalidMsgService;
import com.foxconn.web.mo.req.QueueInvalidMsgListMo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 队列失效消息控制层
 * @Author: 王俊辉
 * @Date: 2019/12/5 11:08 上午
 */
@RestController
@RequestMapping("invalidQueue")
public class QueueInvalidMsgController {

    @Autowired
    QueueInvalidMsgService queueInvalidMsgService;

    /**
     * 获取队列名列表
     * @Author: 王俊辉
     * @Date: 2019/12/5 11:22 上午
     * @Param: [queueInvalidMsgListMo]
     * @Return: com.foxconn.common.mo.ResponseMO
     */
    @RequestMapping("getQueueNames")
    public ResponseMO getQueueNames(){
        List<String> list = queueInvalidMsgService.getQueueNames();
        return ResponseUtil.successWithData(list);
    }

    /**
     * 队列失效消息列表
     * @Author: 王俊辉
     * @Date: 2019/12/5 11:22 上午
     * @Param: [queueInvalidMsgListMo]
     * @Return: com.foxconn.common.mo.ResponseMO
     */
    @RequestMapping("list")
    public ResponseMO list(QueueInvalidMsgListMo queueInvalidMsgListMo){
        PageDataMO pageData = queueInvalidMsgService.getQueueInvalidMsgList(queueInvalidMsgListMo);
        return ResponseUtil.successWithData(pageData);
    }

    /**
     * 失效队列消息失效操作
     * @Author: 王俊辉
     * @Date: 2019/12/5 6:00 下午
     * @Param: [id]
     * @Return: com.foxconn.common.mo.ResponseMO
     */
    @RequestMapping("invalidRecord")
    public ResponseMO invalidRecord(Integer id){
        queueInvalidMsgService.invalidRecord(id);
        return ResponseUtil.success("操作成功。");
    }

    /**
     * 失效队列消息重发操作
     * @Author: 王俊辉
     * @Date: 2019/12/5 6:00 下午
     * @Param: [id]
     * @Return: com.foxconn.common.mo.ResponseMO
     */
    @RequestMapping("rebackQueueById")
    public ResponseMO rebackQueueById(@NotNull Integer id){
        queueInvalidMsgService.rebackQueueById(id);
        return ResponseUtil.success("重发成功。");
    }



}
