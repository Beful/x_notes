package com.xiaoxin.notes.utils;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 26727
 */
@Data
@Accessors(chain = true)
public class R<E> {

    private Integer state;
    private String message;
    private E data;

    public static R ok() {
        return new R().setState(State.OK);
    }

    public static <E> R ok(E data) {
        return new R().setState(State.OK).setData(data);
    }

    public static R failure(Integer state, String message) {
        return new R().setState(state).setMessage(message);
    }

    public static R failure(Integer state, Throwable e) {
        return new R().setState(state).setMessage(e.getMessage());
    }

    /**未登录返回结果*/
    public static <E> R unauthorized(E data) { return new R().setState(State.AA).setMessage("没有相关权限").setData(data); }
    /**未授权返回结果*/
    public static <E> R forbidden(E data) { return new R().setState(State.NO).setMessage("没有相关权限").setData(data); }

    public interface State {
        int OK = 2000;// 2000 请求成功
        int ERR_Error = 4000;// 4000 出现错误
        int PARAMS_ERROR=400;// 400 请求的地址不存在或者包含不支持的参数
        int AA=401;// 401	未授权 暂未登录或token已经过期
        int NO=403;// 403	被禁止访问 没有相关权限
        int NOT_FOUND=404;// 404	请求的资源不存在 参数检验失败
    }



}
