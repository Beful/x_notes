package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章评论表
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 10:58:34
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
	
}
