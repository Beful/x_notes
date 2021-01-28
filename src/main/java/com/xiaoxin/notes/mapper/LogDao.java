package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 *
 * @date 2021-01-12 20:18:07
 */
@Mapper
public interface LogDao extends BaseMapper<LogEntity> {
	
}
