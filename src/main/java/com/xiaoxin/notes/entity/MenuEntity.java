package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Data
@TableName("t_menu")
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单id
	 */
	@TableId(type = IdType.AUTO)
	private Integer mid;
	/**
	 * 菜单编号
	 */
	private String mcode;
	/**
	 * 菜单名称
	 */
	private String navname;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 是否显示
	 */
	private Integer hidden;
	/**
	 * 父节点
	 */
	private String fathernode;
	/**
	 * 顺序
	 */
	private String orders;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 标签
	 */
	private String icon;
	/**
	 * 组件
	 */
	private String component;
	//子菜单列表
	@TableField(exist = false)
	private List<MenuEntity> children;

}
