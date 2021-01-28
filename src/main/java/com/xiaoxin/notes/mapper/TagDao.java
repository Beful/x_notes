package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 标签表
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 10:58:34
 */
@Mapper
public interface TagDao extends BaseMapper<TagEntity> {

    @Select("select t.id,t.name,t.type,t.size,t.color,t.create_time,t.is_use from t_ariticle_tag t " +
            "left join t_ariticle_tag_relation atr on t.id = atr.tag_id " +
            "where atr.ariticle_id = #{ariticle_id} and is_use = 1")
    List<TagEntity> selTagsByAriticle(Integer ariticle_id);
}
