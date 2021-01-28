package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author 
 * @email ${email}
 * @date 2021-01-23 16:17:52
 */
@Data
@TableName("t_leaver_board")
public class LeaverBoardEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private Integer userid;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 评论等级[ 1 一级评论 默认 ，other 父评论id]
	 */
	private Integer commentLevel;
	/**
	 * 0 显示  1不显示（删除）
	 */
	private Integer isDel;

	private String commentUser;

	@TableField(exist = false)
	private List<LeaverBoardEntity> children;

}
