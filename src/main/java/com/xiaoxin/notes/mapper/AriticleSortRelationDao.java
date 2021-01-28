package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.AriticleSortRelationEntity;
import com.xiaoxin.notes.entity.SortEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 文章-分类关联表
 * 
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
@Mapper
public interface AriticleSortRelationDao extends BaseMapper<AriticleSortRelationEntity> {

    void saveSortByAri(@Param("list") List<AriticleSortRelationEntity> list);

    @Select("select s.* from t_ariticle_sort_relation asr " +
            "  left join t_ariticle_sort s on asr.sort_id = s.id " +
            "where asr.ariticle_id = #{id}")
    List<SortEntity> selSortListByAid(Integer id);

    @Select("select a.id,a.title,a.user_id from t_ariticle_sort_relation asr left join t_ariticle a on a.id = asr.ariticle_id where asr.sort_id = '3' and is_publish = '1' and is_del = '0'")
    List<AriticleEntity> selKnowLearn();
}
