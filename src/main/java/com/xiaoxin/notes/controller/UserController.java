package com.xiaoxin.notes.controller;

import com.xiaoxin.notes.entity.PersonNotesEntity;
import com.xiaoxin.notes.entity.RoleEntity;
import com.xiaoxin.notes.entity.UserEntity;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.service.RoleService;
import com.xiaoxin.notes.service.UserService;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 系统用户
 *
 * @date 2020-12-19 22:19:45
 */
@Api(tags = "UserController", description = "系统用户")
@RestController
@RequestMapping("notes/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "用户列表")
    @GetMapping("list")
    public R list() {
        List<UserEntity> list = userService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("insertOneUser")
    public R insertOneUser(@RequestBody UserEntity user) {
        userService.selectUserCount(user.getUsername());
        user.setCreateTime(new Date());
        boolean save = userService.save(user);
        if(!save){
            throw new RunServerException("添加用户出现错误！");
        }
        return R.ok();
    }

    @ApiOperation(value = "查询用户信息")
    @GetMapping("selOneUser")
    public R selOneUser(@RequestParam String id) {
        UserEntity userEntity = userService.selOneUser(id);
        return R.ok(userEntity);
    }

    @ApiOperation(value = "编辑用户")
    @PostMapping("editOneUser")
    public R editOneUser(@RequestBody UserEntity user) {
        boolean b = userService.updateById(user);
        if (!b) {
            throw new RunServerException("修改用户失败！");
        }
        return R.ok();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("delOneUser/{id}")
    public R delOneUser(@PathVariable("id") String id) {
        userService.delById(id);
        return R.ok();
    }

    @ApiOperation(value = "用户角色列表")
    @GetMapping("getUserRoleList")
    public R getUserRoleList() {
        List<RoleEntity> list = roleService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "用户对应角色")
    @GetMapping("selRoleByUserId")
    public R selRoleByUserId(@RequestParam String id) {
        List<Integer> list = userService.selRoleByUserId(id);
        return R.ok(list);
    }

    @ApiOperation(value = "插入用户角色")
    @PostMapping("insertUserRole")
    public R insertUserRole(@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "rids",required = false) String rids) {
        userService.saveRolesByUserId(userId,rids);
        return R.ok();
    }

    @ApiOperation(value = "获取个人中心列表")
    @GetMapping("getPersonHubList")
    public R getPersonHubList() {
        List<PersonNotesEntity> list = userService.getPersonHubList();
        return R.ok(list);
    }

    @ApiOperation(value = "保存个人中心")
    @PostMapping("savePersonHub")
    public R savePersonHub(@RequestBody PersonNotesEntity personNotes) {
        userService.savePerNotes(personNotes);
        return R.ok();
    }

}
