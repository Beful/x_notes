package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 *
 * @date 2021-01-12 20:18:07
 */
@Data
@TableName("t_log")
public class LogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	private String title;
	/**
	 * 操作时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 操作内容
	 */
	private String content;
	/**
	 * Ip
	 */
	private String ip;
	/**
	 * t_user 用户表id
	 */
	private Integer userId;

	public LogEntity(String username,String title, String content, String ip) {
		this.username = username;
		this.title = title;
		this.content = content;
		this.ip = ip;
		this.userId = userId;
	}

	public LogEntity() {

	}
}
