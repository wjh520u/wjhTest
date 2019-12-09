package com.foxconn.constant;

/**
 * 锁前缀常量
 * 锁表字段应以表名加字段如：tableName.columnName，如果有字段冲突，请升级成行锁统一使用表名
 * @Author: 王俊辉
 * @Date: 2019/12/6 3:11 下午
 */
public class LockPrefixConstans {

    /**
     *
     * @Author: 王俊辉
     * @Date: 2019/12/6 3:17 下午
     */
    public static final String REBACK_QUEUE = "queue_invalid_msg.status";

}
