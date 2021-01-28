package com.xiaoxin.notes.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
@Data
public class LikedCountDTO implements Serializable {

    private static final long serialVersionUID = 4927486870190985424L;
    private String key; // 文章评论id
    private Integer value; // 点赞数

    LikedCountDTO() {}
    public LikedCountDTO(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
