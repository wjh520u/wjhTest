package com.foxconn.common.mo;


import com.foxconn.constant.CommonConstants;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseMO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code = CommonConstants.RESPONSE_CODE_SUCCESS;  //成功 为0   失败 为 1
    private String msg;
    private Object data;
    private String debugInfo;

    public ResponseMO() {
    }

    public boolean checkFailure() {
        boolean result = false;
        if (this.code == CommonConstants.RESPONSE_CODE_FAILURE) {
            result = true;
        }
        return result;
    }

    public void setResponseCodeFailure() {
        this.code = CommonConstants.RESPONSE_CODE_FAILURE;
    }



}

