package com.xiaoxin.notes.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * Created on 2021/1/31.
 *
 * @author XiaoXinZai
 */
@Getter
public enum  GenderStatusEnum implements IEnum<Integer> {
    /**
     *
     */
    NOENABLE(0,"女"),
    ISENABLE(1,"男");

    private int key;
    private String value;

    GenderStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return this.key;
    }

}
