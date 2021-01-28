package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxin.notes.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.function.Predicate;

/**
 * 用户权限表
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
@Mapper
public interface PermissionDao extends BaseMapper<PermissionEntity> {

}
