package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.QuartzEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-18 20:58:32
 */
@Mapper
public interface QuartzDao extends BaseMapper<QuartzEntity> {
	
}
