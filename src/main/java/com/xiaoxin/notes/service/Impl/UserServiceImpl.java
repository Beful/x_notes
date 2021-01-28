package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.*;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.mapper.PersonNotesDao;
import com.xiaoxin.notes.mapper.UserDao;
import com.xiaoxin.notes.mapper.UserRoleDao;
import com.xiaoxin.notes.service.UserService;
import com.xiaoxin.notes.utils.IPUtils;
import com.xiaoxin.notes.utils.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private PersonNotesDao personNotesDao;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    @Override
    public void submitReg(UserEntity user) {
        int i1 = baseMapper.selectUserNameByCount(user.getUsername());
        if (i1 > 0) {
            throw new RunServerException("用户名已被注册！");
        }
        user.setCreateTime(new Date());
        user.setStatus(1);
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        int insert = baseMapper.insert(user);
        if (insert != 1) {
            throw new RunServerException("注册账户失败！");
        }
        // 分配角色
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(user.getUserId());
        userRoleEntity.setRoleId(Constant.ROLEID);
        userRoleDao.insert(userRoleEntity);

    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserEntity user = baseMapper.selectPwdSaltByUsername(username);
            if (user == null) {
                throw new RunServerException("用户名不存在！");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new RunServerException("密码不正确");
            }
            if (0 == user.getStatus()) {
                throw new RunServerException("用户已禁用，请联系管理员开启！！");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public void selectUserCount(String username) {
        Integer integer = baseMapper.selectCount(new QueryWrapper<UserEntity>().eq("username", username));
        if (integer > 0) {
            throw new RunServerException("用户已被注册！");
        }
    }

    @Override
    public UserEntity selOneUser(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
    }

    @Override
    public List<PermissionEntity> getPermissionList(Integer userId) {
        return baseMapper.getPermissionList(String.valueOf(userId));
    }

    @Override
    public List<Integer> selRoleByUserId(String id) {
        return userRoleDao.selRoleByUserId(id);
    }

    @Override
    public void saveRolesByUserId(String userId, String rids) {
        try {
            userRoleDao.deleteByUserId(userId);
            String[] item = rids.split(",");
            if(item.length > 0 && !StringUtils.isBlank(rids)){
                List<UserRoleEntity> list = new ArrayList<>();
                for (String rid : item) {
                    UserRoleEntity userRoleEntity = new UserRoleEntity();
                    userRoleEntity.setRoleId(Integer.valueOf(rid));
                    userRoleEntity.setUserId(Integer.valueOf(userId));
                    list.add(userRoleEntity);
                }
                userRoleDao.saveRolesByUserId(list);
            }
        }catch (Exception e){
            LOGGER.warn("异常:{}", e.getMessage());
        }
    }

    @Override
    public List<PersonNotesEntity> getPersonHubList() {
        return personNotesDao.selectList(new QueryWrapper<PersonNotesEntity>().orderByDesc("timestamp"));
    }

    @Override
    public void savePerNotes(PersonNotesEntity personNotes) {
        try {
            int insert = personNotesDao.insert(personNotes);
            if(insert == 0){
                throw new RunServerException("保存失败");
            }
        }catch (Exception e){
            LOGGER.warn("异常:{}", e.getMessage());
        }
    }

    @Override
    public void updatePwd(String password, String newPwd, String username) {
        // 用户名查找 判断密码 和 输入password加密 比较
        UserEntity userEntity = baseMapper.selectPwdSaltByUsername(username);
        // 一样
        if(passwordEncoder.matches(password,userEntity.getPassword())){
            UpdateWrapper<UserEntity> qw = new UpdateWrapper<>();
            qw.eq("username",username).set("password",passwordEncoder.encode(newPwd));
            int update = baseMapper.update(null, qw);
            if(update == 0){
                throw new RunServerException("修改密码失败！");
            }
        }else{
            // 不一样
            throw new RunServerException("原密码输入错误，请输入准确信息！");
        }
    }

    @Override
    public void delById(String id) {
        QueryWrapper<UserRoleEntity> qw = new QueryWrapper<UserRoleEntity>()
                .eq("user_id",id);
        Integer integer = userRoleDao.selectCount(qw);
        if (integer > 0) {
            throw new RunServerException("请先删除用户当前分配的角色！");
        }
        int i = baseMapper.deleteById(id);
        if(i == 0){
            throw new RunServerException("删除失败！");
        }
    }

    /**
     * 将密码执行加密
     *
     * @param password 原密码
     * @param salt     盐值
     * @return 加密后的结果
     */
    private String getMd5Password(String password, String salt) {
        // 拼接原密码与盐值
        String str = salt + password + salt;
        // 循环加密5次
        for (int i = 0; i < 5; i++) {
            // DigestUtils：springboot提供的工具类
            str = DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
        }
        return str;
    }
}