package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_person_notes
 * @author 
 */
@Data
@TableName("t_person_notes")
public class PersonNotesEntity implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;
    private String title;

    /**
     * 记录时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date timestamp;

    /**
     * 字体样式
     */
    private String size;

    private String type;

    /**
     * 标签
     */
    private String icon;

    /**
     * 颜色
     */
    private String color;

    private static final long serialVersionUID = 1L;
}