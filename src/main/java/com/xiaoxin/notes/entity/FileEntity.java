package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-11 19:45:32
 */
@Data
@TableName("t_file")
public class FileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 所属业务数据编码 ID或CODE
	 */
	private String ownCode;
	/**
	 * 原始文件名
	 */
	private String orgName;
	/**
	 * 文件名（系统定义非重复文件名 一般为时间戳）
	 */
	private String fileName;
	/**
	 * FTP路径
	 */
	private String ftpPath;
	/**
	 * 上传时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 文件格式（后缀名）
	 */
	private String fileFormat;
	/**
	 * 文件描述
	 */
	private String fileDes;
	/**
	 * 文件类型（t_code代码）
	 */
	private String fileType;
	/**
	 * 文件md5值
	 */
	private String fileMd5;
	/**
	 * 文件大小 默认单位：MB
	 */
	private String fileSize;

}
