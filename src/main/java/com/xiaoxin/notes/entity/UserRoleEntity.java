package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Data
@TableName("t_user_role")
public class UserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.AUTO)
	private Integer id;
	private Integer roleId;
	private Integer userId;

}
