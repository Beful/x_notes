package com.xiaoxin.notes.enums;

import lombok.Getter;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
@Getter
public enum LikedStatusEnum{
        LIKE(1, "点赞"),
        UNLIKE(0, "取消点赞/未点赞"),
        ;
        private Integer code;
        private String msg;
        LikedStatusEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
}
