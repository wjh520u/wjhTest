package com.foxconn.service.controller.impl;

import com.foxconn.common.exception.BaseException;
import com.foxconn.common.mo.PageDataMO;
import com.foxconn.common.utils.LockUtil;
import com.foxconn.common.utils.ResponseUtil;
import com.foxconn.constant.LockPrefixConstans;
import com.foxconn.constant.QueueInvalidMsgDoConstants;
import com.foxconn.entity.QueueInvalidMsgDo;
import com.foxconn.repo.queue.QueueInvalidMsgMapper;
import com.foxconn.service.controller.QueueInvalidMsgService;
import com.foxconn.service.rabbitmq.MQInvalidMessageService;
import com.foxconn.web.mo.req.QueueInvalidMsgListMo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 队列失效消息控制层服务实现
 * @Author: 王俊辉
 * @Date: 2019/12/5 11:29 上午
 */
@Service
public class QueueInvalidMsgServiceImpl implements QueueInvalidMsgService {

    @Autowired
    QueueInvalidMsgMapper queueInvalidMsgMapper;

    @Autowired
    MQInvalidMessageService mqInvalidMessageService;

    /**
     * 获取队列失效消息列表
     * @Author: 王俊辉
     * @Date: 2019/12/5 11:34 上午
     * @Param: [queueInvalidMsgListMo]
     * @Return: java.util.List<com.foxconn.entity.QueueInvalidMsgDo>
     */
    @Override
    public PageDataMO getQueueInvalidMsgList(QueueInvalidMsgListMo queueInvalidMsgListMo) {
        PageHelper.startPage(queueInvalidMsgListMo.getPage() , queueInvalidMsgListMo.getLimit());
        List<QueueInvalidMsgDo> queueInvalidMsgList = queueInvalidMsgMapper.getQueueInvalidMsgList(queueInvalidMsgListMo);
        PageInfo<QueueInvalidMsgDo> queueInvalidMsgDoInfo = new PageInfo<>(queueInvalidMsgList);
        PageDataMO pageData = new PageDataMO(queueInvalidMsgDoInfo.getTotal(), queueInvalidMsgDoInfo.getList());
        return pageData;
    }

    /**
     * 失效队列消息失效操作
     * @Author: 王俊辉
     * @Date: 2019/12/5 6:01 下午
     * @Param: [id]
     * @Return: void
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void invalidRecord(Integer id) {
        QueueInvalidMsgDo queueInvalidMsgDoUpdate = new QueueInvalidMsgDo();
        queueInvalidMsgDoUpdate.setId(id);
        queueInvalidMsgDoUpdate.setStatus(QueueInvalidMsgDoConstants.STATUS_INVALID);
        int affect = queueInvalidMsgMapper.updateByPrimaryKeySelective(queueInvalidMsgDoUpdate);
        if (affect == 0){
            throw new BaseException(ResponseUtil.error("操作失败"));
        }
    }

    /**
     * 失效队列消息重发操作
     * @Author: 王俊辉
     * @Date: 2019/12/6 2:53 下午
     * @Param: [id]
     * @Return: void
     */
    @Override
    public void rebackQueueById(Integer id) {

        String lockKey =  LockPrefixConstans.REBACK_QUEUE + id.toString();
        LockUtil.lock(lockKey, ()->{
            mqInvalidMessageService.sendRebackQueue(id);
            return null;
        });

    }

    /**
     * 获取队列名列表
     * @Author: 王俊辉
     * @Date: 2019/12/7 10:42 上午
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     */
    @Override
    public List<String> getQueueNames() {
        List<String> list = queueInvalidMsgMapper.selectQueueNames();
        return list;
    }

}
