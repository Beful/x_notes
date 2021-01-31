package com.xiaoxin.notes.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * Created on 2021/1/31.
 *
 * @author XiaoXinZai
 */
@Getter
public enum EnableStatusEnum implements IEnum<Integer> {
    /**
     *
     */
    NOENABLE(0,"禁用"),
    ISENABLE(1,"启用");

    private int key;
    private String value;

    EnableStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return this.key;
    }

}
