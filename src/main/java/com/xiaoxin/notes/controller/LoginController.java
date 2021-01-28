package com.xiaoxin.notes.controller;


import cn.hutool.core.date.DateUtil;
import com.xiaoxin.notes.entity.LogEntity;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.service.LogService;
import com.xiaoxin.notes.service.MenuService;
import com.xiaoxin.notes.service.UserService;
import com.xiaoxin.notes.utils.IPUtils;
import com.xiaoxin.notes.utils.R;
import com.xiaoxin.notes.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 26727
 */
@Api(tags = "LoginController", description = "用户首页管理")
@RestController
@RequestMapping("userLogin")
public class LoginController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @PostMapping("submitReg")
    public R submitReg(@RequestBody UserEntity user){
        userService.submitReg(user);
        return R.ok();
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("submitLogin")
    public R submitLogin(@RequestBody UserEntity user, HttpServletRequest request){
        String token = userService.login(user.getUsername(), user.getPassword());
        if (token == null) {
            return R.failure(R.State.ERR_Error,"用户名或密码错误！");
        }
        logService.save(new LogEntity(user.getUsername(),"登录",DateUtil.now() + "：" + user.getUsername() + "登录",IPUtils.getIpAddr(request)));
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("userId", String.valueOf(userService.getUserByUsername(user.getUsername()).getUserId()));
        return R.ok(tokenMap);
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("updatePwd")
    public R updatePwd(String password,String newPwd,String username,HttpServletRequest request){
        userService.updatePwd(password,newPwd,username);
        logService.save(new LogEntity(username,"修改密码", "修改密码,原密码：" + password + "新密码：" + newPwd, IPUtils.getIpAddr(request)));
        return R.ok();
    }

    @ApiOperation(value = "主页菜单列表")
    @GetMapping("menuList")
    public R menuList(@RequestParam String username){
        List<MenuEntity> list = menuService.userMenuList(username);
        return R.ok(list);
    }


}
