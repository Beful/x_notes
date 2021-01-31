package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.config.RedisConfig;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.entity.enums.EnableStatusEnum;
import com.xiaoxin.notes.mapper.MenuDao;
import com.xiaoxin.notes.service.MenuService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import com.xiaoxin.notes.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void selectMenuCount(String navname) {
        Integer integer = baseMapper.selectCount(new QueryWrapper<MenuEntity>().eq("navname", navname));
        if(integer > 0){
            throw new RunServerException("菜单已添加，请确认菜单内容！");
        }
    }

    @Override
    public MenuEntity selOneMenu(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<MenuEntity> menuList() {
        List<MenuEntity> entities = baseMapper.selectList(null);

        // 找出所有一级分类
        List<MenuEntity> level1Menus  = entities.stream().filter(menu -> {
            return Constant.MENUFATHERID.equals(menu.getFathernode()) && EnableStatusEnum.ISENABLE == menu.getHidden();
        }).map(menu -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted(Comparator.comparingInt(menu -> (menu.getOrders() == null ? 0 : Integer.valueOf(menu.getOrders())))).collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public List<MenuEntity> redisMenuList() {
        return redisTemplate.opsForList().range(RedisConfig.LIST_KEY_MENULIST,0,-1);
    }

    @Override
    public PageUtils selMenuListAsc(Map<String,Object> params) {
        String query = params.get("query").toString();
        IPage<MenuEntity> page = this.page(new QueryPage<MenuEntity>().getPage(params),
                new QueryWrapper<MenuEntity>()
                        .like(StringUtils.isNotBlank(query),"navname", query)
        );
        return new PageUtils(page);
    }

    @Override
    public void delOneMenu(String id) {
        try {
            int i = baseMapper.deleteById(id);
            if (i == 0) {
                throw new RunServerException("删除菜单失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<MenuEntity> userMenuList(String username) {
        List<MenuEntity> entities = baseMapper.userMenuList(username);

        // 找出所有一级分类
        List<MenuEntity> level1Menus  = entities.stream().filter(menu -> {
            return Constant.MENUFATHERID.equals(menu.getFathernode()) && EnableStatusEnum.ISENABLE == menu.getHidden();
        }).map(menu -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted((menu1,menu2) -> {
            return (menu1.getOrders() == null ? 0 : Integer.valueOf(menu1.getOrders())) - (menu2.getOrders() == null ? 0 : Integer.valueOf(menu2.getOrders()));
        }).collect(Collectors.toList());

        return level1Menus;
    }

    //递归查找所有菜单的子菜单
    private List<MenuEntity> getChildren(MenuEntity root,List<MenuEntity> all) {
        List<MenuEntity> childre = all.stream().filter(menu -> {
            return menu.getFathernode().equals(root.getMcode()) && EnableStatusEnum.ISENABLE == menu.getHidden();
        }).map(menu -> {
            menu.setChildren(getChildren(menu, all));
            return menu;
        }).sorted((menu1, menu2) -> {
            return (menu1.getOrders() == null ? 0 : Integer.valueOf(menu1.getOrders())) - (menu2.getOrders() == null ? 0 : Integer.valueOf(menu2.getOrders()));
        }).collect(Collectors.toList());

        return childre;
    }



}