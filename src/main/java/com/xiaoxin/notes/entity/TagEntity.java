package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 标签表
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 10:58:34
 */
@Data
@TableName("t_ariticle_tag")
public class TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String size;
	/**
	 * 
	 */
	private String color;
	/**
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 是否使用 0->关闭 1->开启
	 */
	private String isUse;

}
