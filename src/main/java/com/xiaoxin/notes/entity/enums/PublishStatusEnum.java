package com.xiaoxin.notes.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * Created on 2021/1/31.
 *
 * @author XiaoXinZai
 */
@Getter
public enum  PublishStatusEnum implements IEnum<Integer> {
    /**
     *
     */
    NOPUBLISH(0,"未发布"),
    ISPUBLISH(1,"已发布");

    private int key;
    private String value;

    PublishStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return this.key;
    }

}
