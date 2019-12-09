package com.foxconn.common.mo;

import lombok.Data;
/**
 * 通用分页参数
 * @Author: 王俊辉
 * @Date: 2019/12/5 5:51 下午
 */
public class PageMO {

    private Integer page;

    private Integer limit;

    public Integer getPage() {
        if (page == null){
            return 1;
        }else if (page < 1){
            return 1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        if (limit == null){
            return 10;
        }else if (limit < 1 || limit > 100) {
            return 10;
        }
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
