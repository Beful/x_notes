package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
public interface MenuService extends IService<MenuEntity> {

    void selectMenuCount(String navname);

    MenuEntity selOneMenu(String id);

    /** 菜单分类 children 子菜单  */
    List<MenuEntity> menuList();

    /** 菜单分类 children 子菜单  */
    List<MenuEntity> redisMenuList();

    PageUtils selMenuListAsc(Map<String,Object> params);

    void delOneMenu(String id);

    List<MenuEntity> userMenuList(String username);
}

