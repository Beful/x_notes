package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.entity.RoleMenuEntity;
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
public interface RoleMenuDao extends BaseMapper<RoleMenuEntity> {

    @Insert("<script>" +
            "insert into t_role_menu(" +
            "   role_id,menu_id" +
            ") values " +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "   (" +
            "   #{item.roleId},#{item.menuId}" +
            "   ) " +
            "</foreach>" +
            "</script>")
    Integer saveMenusByPerId(List<RoleMenuEntity> list);

    @Select("select mid,fathernode from t_menu " +
            "where mid in (select menu_id from t_role_menu where role_id = #{id})")
    List<MenuEntity> selMenuByRoleId(String id);

    @Delete("delete from t_role_menu where role_id = #{roleId}")
    void deleteByRoleId(String roleId);
}
