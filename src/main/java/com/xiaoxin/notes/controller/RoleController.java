package com.xiaoxin.notes.controller;


import com.xiaoxin.notes.entity.RoleEntity;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.service.*;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 
 *
 * @date 2020-12-19 22:19:45
 */
@Api(tags = "RoleController", description = "角色管理")
@RestController
@RequestMapping("notes/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value="角色列表")
    @GetMapping("list")
    public R selList(@RequestParam String query,@RequestParam int pageSize,@RequestParam int pagenum) {
        PageUtils pageUtils = roleService.selList(query, pageSize, pagenum);
        return R.ok(pageUtils);
    }

    @ApiOperation(value="插入角色列表")
    @PostMapping("insertOneRole")
    @PreAuthorize("hasAuthority('notes:role:insert')")
    public R insertOneRole(@RequestBody RoleEntity role) {
        roleService.selectRoleCount(role.getRname());
        role.setCreatetime(new Date());
        boolean save = roleService.save(role);
        if(!save){
            throw new RunServerException("添加角色出现错误！");
        }
        return R.ok();
    }

    @ApiOperation(value="查询角色信息")
    @GetMapping("selOneRole")
    public R selOneRole(@RequestParam String id) {
        RoleEntity roleEntity = roleService.selectById(id);
        return R.ok(roleEntity);
    }

    @ApiOperation(value="编辑角色信息")
    @PostMapping("editOneRole")
    @PreAuthorize("hasAuthority('notes:role:update')")
    public R editOneRole(@RequestBody RoleEntity role) {
        role.setUpdatetime(new Date());
        boolean b = roleService.updateById(role);
        if (!b) {
            throw new RunServerException("修改权限失败！");
        }
        return R.ok();
    }

    @ApiOperation(value="删除角色信息")
    @DeleteMapping("delOneRole/{id}")
    @PreAuthorize("hasAuthority('notes:role:delete')")
    public R delOneRole(@PathVariable("id") String id) {
        roleService.delOneRole(id);
        return R.ok();
    }

    @ApiOperation(value="角色对应菜单信息")
    @GetMapping("selPerByRoleId")
    public R selPerByRoleId(@RequestParam String id) {
        List<Integer> list = roleService.selPerByRoleId(id);
        return R.ok(list);
    }

    @ApiOperation(value="插入角色权限")
    @PostMapping("insertRolePre")
    @PreAuthorize("hasAuthority('notes:role:insertPer')")
    public R insertRolePre(@RequestParam(value = "roleId",required = false) String roleId,@RequestParam(value = "pids",required = false) String pids) {
        roleService.savePersByRoleId(roleId,pids);
        return R.ok();
    }

    @ApiOperation(value="角色对应菜单信息")
    @GetMapping("selMenuByRoleId")
    public R selMenuByPerId(@RequestParam String id) {
        List<Integer> list = roleService.selMenuByRoleId(id);
        return R.ok(list);
    }

    @ApiOperation(value="插入角色菜单信息")
    @PostMapping("insertRoleMenu")
    @PreAuthorize("hasAuthority('notes:role:insertMenu')")
    public R insertPerMenu(@RequestParam(value = "roleId",required = false) String roleId,@RequestParam(value = "mids",required = false) String mids) {
        roleService.saveMenusByRoleId(roleId,mids);
        return R.ok();
    }

}
