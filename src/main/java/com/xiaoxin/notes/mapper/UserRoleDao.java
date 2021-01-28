package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.UserEntity;
import com.xiaoxin.notes.entity.UserRoleEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {

    @Select("select role_id from t_user_role where user_id = #{id}")
    List<Integer> selRoleByUserId(@Param("id") String id);

    @Insert("<script>" +
            "insert into t_user_role(" +
            "   user_id,role_id " +
            ") values " +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "   (" +
            "   #{item.userId},#{item.roleId}" +
            "   ) " +
            "</foreach>" +
            "</script>")
    Integer saveRolesByUserId(List<UserRoleEntity> list);

    @Delete("delete from t_user_role where user_id = #{userId}")
    void deleteByUserId(String userId);
}
