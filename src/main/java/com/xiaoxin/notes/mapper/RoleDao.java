package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xiaoxin.notes.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Mapper
public interface RoleDao extends BaseMapper<RoleEntity> {

    @Select("select * from t_role ${ew.customSqlSegment}")
    IPage<RoleEntity> page(IPage<RoleEntity> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
