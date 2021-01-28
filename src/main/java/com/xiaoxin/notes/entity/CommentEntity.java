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
 * 文章评论表
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 10:58:34
 */
@Data
@TableName("t_ariticle_comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 评论id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 评论人userId
	 */
	private String userId;
	/**
	 * 评论人名称
	 */
	private String userName;
	/**
	 * 评论的文章id
	 */
	private String articleId;
	/**
	 * 评论的文章标题
	 */
	private String articleTitle;
	/**
	 * 父评论id
	 */
	private String parentCommentId;
	/**
	 * 父评论的用户id
	 */
	private String parentCommentUserId;
	/**
	 * 被回复的评论id
	 */
	private String replyCommentId;
	/**
	 * 被回复的评论用户id
	 */
	private String replyCommentUserId;
	/**
	 * 评论等级[ 1 一级评论 默认 ，2 二级评论]
	 */
	private Integer commentLevel;
	/**
	 * 评论的内容
	 */
	private String content;
	/**
	 * 状态 (1 有效，0 逻辑删除)
	 */
	private Integer status;
	/**
	 * 点赞数
	 */
	private Integer praiseNum;
	/**
	 * 置顶状态[ 1 置顶，0 不置顶 默认 ]
	 */
	private Integer topStatus;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;

	@TableField(exist = false)
	private List<CommentEntity> children;

	@TableField(exist = false)
	private UserEntity userChild;

	@TableField(exist = false)
	private String timeStamp;

}
