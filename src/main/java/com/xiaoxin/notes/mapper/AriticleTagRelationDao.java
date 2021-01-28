package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.AriticleTagRelationEntity;
import com.xiaoxin.notes.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文章-标签关联表
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
@Mapper
public interface AriticleTagRelationDao extends BaseMapper<AriticleTagRelationEntity> {


    void saveTagByAri(List<AriticleTagRelationEntity> tagList);
}
