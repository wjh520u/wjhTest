package com.foxconn.web.mo.req;

import com.foxconn.common.mo.PageMO;
import lombok.Data;

/**
 *
 * @Author: 王俊辉
 * @Date: 2019/12/5 11:22 上午
 */
@Data
public class QueueInvalidMsgListMo extends PageMO {

    private String source;//数据来源

    private String queueName;//队列名称

}
