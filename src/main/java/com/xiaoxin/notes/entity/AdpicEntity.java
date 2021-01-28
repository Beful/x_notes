package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 *
 * @date 2021-01-13 14:01:44
 */
@Data
@TableName("t_adpic")
public class AdpicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 模块
	 */
	private String model;
	/**
	 * 图片地址
	 */
	private String img;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 开始时间
	 */
	private Date uptime;
	/**
	 * 下架时间
	 */
	private Date downtime;
	/**
	 * 排序
	 */
	private Integer ordernum;

	@TableField(exist = false)
	private List<String> photo;

}
