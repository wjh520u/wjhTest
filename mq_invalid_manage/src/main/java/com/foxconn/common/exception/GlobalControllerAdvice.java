package com.foxconn.common.exception;

import com.foxconn.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public Object errorHandler(Throwable ex) {
        if (ex instanceof BaseException){
            log.info(ex.toString());
            return ((BaseException) ex).getResponseMO();
        }
        ex.printStackTrace();
        return ResponseUtil.error("系统异常。", ex.getMessage());
    }

}
