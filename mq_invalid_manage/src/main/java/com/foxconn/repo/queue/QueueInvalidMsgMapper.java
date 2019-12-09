package com.foxconn.repo.queue;

import com.foxconn.entity.QueueInvalidMsgDo;
import com.foxconn.web.mo.req.QueueInvalidMsgListMo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * 异常消息表Mapper
 */
@Repository
public interface QueueInvalidMsgMapper extends BaseMapper<QueueInvalidMsgDo> {

    /**
     * 获取队列失效消息列表
     * @Author: 王俊辉
     * @Date: 2019/12/6 9:38 上午
     * @Param: [queueInvalidMsgListMo]
     * @Return: java.util.List<com.foxconn.entity.QueueInvalidMsgDo>
     */
    List<QueueInvalidMsgDo> getQueueInvalidMsgList(QueueInvalidMsgListMo queueInvalidMsgListMo);

    /**
     * 获取队列列表
     * @Author: 王俊辉
     * @Date: 2019/12/7 10:44 上午
     * @Param: []
     * @Return: java.util.List<java.lang.String>
     */
    @Select("SELECT queue_name FROM queue_invalid_msg WHERE status = 0 GROUP BY queue_name")
    List<String> selectQueueNames();
}