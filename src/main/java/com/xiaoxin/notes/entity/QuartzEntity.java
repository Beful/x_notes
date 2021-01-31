package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaoxin.notes.entity.enums.EnableStatusEnum;
import lombok.Data;

/**
 * 定时任务
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-18 20:58:32
 */
@Data
@TableName("t_quartz")
public class QuartzEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private String createUser;
	/**
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 
	 */
	private String updateUser;
	/**
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 定时任务名称
	 */
	private String jobName;
	/**
	 * 表达式
	 */
	private String cron;
	/**
	 * 状态 0->关闭 1->开启
	 */
	private EnableStatusEnum status;
	/**
	 * 定时任务类
	 */
	private String clazzPath;
	/**
	 * 描述
	 */
	private String jobDesc;

}
