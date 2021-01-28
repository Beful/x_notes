package com.xiaoxin.notes.controller.ex;

public class RunServerException extends ServiceException{
    public RunServerException() {
        super();
    }

    public RunServerException(String message) {
        super(message);
    }

    public RunServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunServerException(Throwable cause) {
        super(cause);
    }

    public RunServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
