package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.PermissionEntity;
import com.xiaoxin.notes.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统用户
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email} 个人中心
 * @date 2020-12-19 22:19:45
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    List<PermissionEntity> getPermissionList(String userId);

    @Select("select count(1) from t_user where username = #{username} limit 1")
    int selectUserNameByCount(String username);

    @Select("select * from t_user where username = #{username} limit 1")
    UserEntity selectPwdSaltByUsername(String username);
}
