package com.foxconn.common.exception;

import com.foxconn.common.mo.ResponseMO;
import com.foxconn.common.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 基础异常
 * @Author: 王俊辉
 * @Date: 2019/12/5 6:05 下午
 */
public class BaseException extends RuntimeException {

    private ResponseMO responseMO;

    public BaseException(){
        super("系统异常。");
        this.responseMO = ResponseUtil.error("系统异常。");
    }

    public BaseException(String message){
        super(message);
        this.responseMO = ResponseUtil.error(message);
    }

    public BaseException(ResponseMO responseMO) {
        super(responseMO.getMsg());
        this.responseMO = responseMO;
    }

    public ResponseMO getResponseMO() {
        return responseMO;
    }

    public void setResponseMO(ResponseMO responseMO) {
        this.responseMO = responseMO;
    }

    @Override
    public String toString() {
        return "BaseException{" +
                "responseMO=" + responseMO +
                '}';
    }
}
