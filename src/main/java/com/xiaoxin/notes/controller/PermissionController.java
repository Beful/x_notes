package com.xiaoxin.notes.controller;


import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.entity.PermissionEntity;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.service.MenuService;
import com.xiaoxin.notes.service.PermissionService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 用户权限表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Api(tags = "PermissionController", description = "权限管理")
@RestController
@RequestMapping("notes/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MenuService menuService;

    @GetMapping("list")
    public R list(@RequestParam String query,@RequestParam int pageSize,@RequestParam int pagenum) {
        Map<String, Object> params = new HashMap<>();
        params.put("query",query);
        params.put(Constant.LIMIT,String.valueOf(pageSize));
        params.put(Constant.PAGE,String.valueOf(pagenum));
        PageUtils list = permissionService.selList(params);
        return R.ok(list);
    }

    @ApiOperation(value="加载权限列表")
    @GetMapping("getPreList")
    public R getPreList() {
        List<PermissionEntity> list = permissionService.list();
        return R.ok(list);
    }

    @GetMapping("getPerMenuList")
    public R getPerMenuList(){
        List<MenuEntity> list = menuService.redisMenuList();
        return R.ok(list);
    }

    @PostMapping("insertOnePer")
    public R insertOnePer(@RequestBody PermissionEntity permission) {
        permissionService.insertOnePer(permission);
        return R.ok();
    }

    @GetMapping("selOnePer")
    public R selOnePer(@RequestParam String id) {
        PermissionEntity permissionEntity = permissionService.selOnePer(id);
        return R.ok(permissionEntity);
    }

    @PostMapping("editOnePer")
    public R editOnePer(@RequestBody PermissionEntity permission) {
        boolean b = permissionService.updateById(permission);
        if (!b) {
            throw new RunServerException("修改权限失败！");
        }
        return R.ok();
    }

    @DeleteMapping("delOnePer/{id}")
    public R delOnePer(@PathVariable("id") String id) {
        boolean b = permissionService.removeById(id);
        if (!b) {
            throw new RunServerException("删除权限失败！");
        }
        return R.ok();
    }

    @ApiOperation(value="加载父权限列表")
    @GetMapping("getSelFids")
    public R getSelFids(){
        List<PermissionEntity> list = permissionService.list();
        List<PermissionEntity> collect = list.stream().filter(permissionEntity -> {
            return permissionEntity.getFid() == 1;
        }).collect(Collectors.toList());

        return R.ok(collect);
    }




}
