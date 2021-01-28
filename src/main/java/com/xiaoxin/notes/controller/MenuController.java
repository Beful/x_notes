package com.xiaoxin.notes.controller;


import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.mapper.RoleMenuDao;
import com.xiaoxin.notes.service.MenuService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 
 *
 * @date 2020-12-19 22:19:45
 */
@Api(tags = "MenuController", description = "菜单")
@RestController
@RequestMapping("notes/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuDao roleMenuDao;

    @GetMapping("list")
    public R list(@RequestParam String query,@RequestParam int pageSize,@RequestParam int pagenum) {
        Map<String, Object> params = new HashMap<>();
        params.put("query",query);
        params.put(Constant.LIMIT,String.valueOf(pageSize));
        params.put(Constant.PAGE,String.valueOf(pagenum));
        PageUtils list = menuService.selMenuListAsc(params);
        return R.ok(list);
    }

    @ApiOperation(value="加载菜单列表")
    @GetMapping("getMenuList")
    public R getMenuList() {
        List<MenuEntity> list = menuService.redisMenuList();
        return R.ok(list);
    }

    @PostMapping("insertOneMenu")
    public R insertOneMenu(@RequestBody MenuEntity menu) {
        menuService.selectMenuCount(menu.getNavname());
        boolean save = menuService.save(menu);
        if(!save){
            throw new RunServerException("添加菜单出现错误！");
        }

        return R.ok();
    }

    @GetMapping("selOneMenu")
    public R selOneMenu(@RequestParam String id) {
        MenuEntity menuEntity = menuService.selOneMenu(id);
        return R.ok(menuEntity);
    }

    @PostMapping("editOneMenu")
    public R editOneMenu(@RequestBody MenuEntity menu) {
        boolean b = menuService.updateById(menu);
        if (!b) {
            throw new RunServerException("修改菜单失败！");
        }
        return R.ok();
    }

    @DeleteMapping("delOneMenu/{id}")
    public R delOneMenu(@PathVariable("id") String id) {
        menuService.delOneMenu(id);
        return R.ok();
    }

}
