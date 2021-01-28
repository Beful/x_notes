package com.xiaoxin.notes.controller.ex;

/**
 * Created on 2021/1/20.
 *
 * @author XiaoXinZai
 */
public class ParamsErrorException extends ServiceException{

    public ParamsErrorException() {
        super();
    }

    public ParamsErrorException(String message) {
        super(message);
    }

    public ParamsErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsErrorException(Throwable cause) {
        super(cause);
    }

    public ParamsErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
