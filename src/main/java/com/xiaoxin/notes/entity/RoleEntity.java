package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Data
@TableName("t_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer roleid;
	/**
	 * 角色编号
	 */
	private String rolecode;
	/**
	 * 角色名称
	 */
	private String rname;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 修改时间
	 */
	private Date updatetime;
	/**
	 * 备注
	 */
	private String remark;

}
