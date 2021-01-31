package com.xiaoxin.notes.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * Created on 2021/1/30.
 *
 * @author XiaoXinZai
 */
@Getter
public enum DelStatusEnum implements IEnum<Integer> {
    /**  */
    NODEL(0, "未删除"),
    ISDEL(1, "已删除");

    private int key;
    private String value;

    DelStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return this.key;
    }

}
