package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.AriticleSortRelationEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 文章-分类关联表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
public interface AriticleSortRelationService extends IService<AriticleSortRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AriticleEntity> selKnowLearn();
}

