package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaoxin.notes.entity.enums.DelStatusEnum;
import com.xiaoxin.notes.entity.enums.PublishStatusEnum;
import lombok.Data;

/**
 * 文章表
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
@Data
@TableName("t_ariticle")
public class AriticleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String content;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 浏览数
	 */
	private Integer browseNum;
	/**
	 * 点赞数
	 */
	private Integer likeNum;
	/**
	 * 评论数
	 */
	private Integer commentNum;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 发布时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date publishTime;
	/**
	 * 删除状态 0->未删除 1->已删除
	 */
	private DelStatusEnum isDel;
	/**
	 * 发布状态 0->未发布 1->已发布
	 */
	private PublishStatusEnum isPublish;

	private String type;

	private String originalLink;

	private String summary;

	private String userId;

	@TableField(exist = false)
	private List<TagEntity> tagList;

	@TableField(exist = false)
	private List<SortEntity> sortList;

	@TableField(exist = false)
	private List<Integer> tags;

	@TableField(exist = false)
	private List<Integer> sorts;
}
