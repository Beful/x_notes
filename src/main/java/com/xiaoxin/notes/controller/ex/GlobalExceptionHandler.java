package com.xiaoxin.notes.controller.ex;


import com.xiaoxin.notes.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局处理异常的类
 * @author 26727
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public R handleException(Throwable e) {
        if (e instanceof RunServerException) {
            return R.failure(R.State.ERR_Error, e);
        } else if (e instanceof NotFoundException) {
            return R.failure(R.State.NOT_FOUND, e);
        } else if (e instanceof ParamsErrorException) {
            return R.failure(R.State.PARAMS_ERROR, e);
        }
        return R.failure(R.State.ERR_Error,e);
    }

}
