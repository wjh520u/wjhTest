package com.foxconn.common.mo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用分页数据
 * @Author: 王俊辉
 * @Date: 2019/12/5 5:52 下午
 */
@Data
public class PageDataMO {

    public PageDataMO(Long count, Object data) {
        this.count = count;
        this.data = data;
    }

    private Integer code = 0;

    private Long count;

    private Object data;

}
