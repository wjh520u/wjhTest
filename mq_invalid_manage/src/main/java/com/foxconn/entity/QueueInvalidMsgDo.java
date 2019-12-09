package com.foxconn.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 异常消息表Entity
 */
@Entity
@Table(name = "queue_invalid_msg")
@Data
public class QueueInvalidMsgDo {

    @Id
    @Column(name = "id")
    private Integer id;//自增ID

    @Column(name = "source")
    private String source;//数据来源

    @Column(name = "queue_name")
    private String queueName;//队列名称

    @Column(name = "queue_routing_key")
    private String queueRoutingKey;//路由键

    @Column(name = "exchange")
    private String exchange;//交换机名称

    @Column(name = "queue_message")
    private String queueMessage;//消息原数据

    @Column(name = "message_type")
    private String messageType;//消息数据类型（json或其它)

    @Column(name = "description")
    private String description;//消息描述说明

    @Column(name = "status")
    private Integer status;//记录状态（0：正常，1：已删除，2: ：无效）

    @Column(name = "inputedtime")
    private Date inputedtime;//录入时间

    @Column(name = "updatedtime")
    private Date updatedtime;//更新时间

}
