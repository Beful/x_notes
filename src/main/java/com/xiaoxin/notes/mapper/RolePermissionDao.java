package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermissionEntity> {

    @Select("select permission_id from t_role_permission where role_id = #{id}")
    List<Integer> selPerByRoleId(String id);

    @Delete("delete from t_role_permission where role_id = #{roleId}")
    void deleteByRoleId(String roleId);

    @Insert("<script>" +
            "insert into t_role_permission(" +
            "   role_id,permission_id" +
            ") values " +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "   (" +
            "   #{item.roleId},#{item.permissionId}" +
            "   ) " +
            "</foreach>" +
            "</script>")
    void savePersByRoleId(List<RolePermissionEntity> list);
}
