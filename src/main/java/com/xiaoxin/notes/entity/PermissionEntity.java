package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户权限表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Data
@TableName("t_permission")
public class PermissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long pid;
	private Long fid;
	// 權限值
	private String value;
	private String icon;
	//权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
	private int type;
	//启用状态；0->禁用；1->启用
	private int status;
	private String pname;
	private String url;
	private String sort;
	private String createTime;

}
