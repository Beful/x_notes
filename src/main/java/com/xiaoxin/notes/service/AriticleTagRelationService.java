package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.AriticleTagRelationEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.Map;

/**
 * 文章-标签关联表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
public interface AriticleTagRelationService extends IService<AriticleTagRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

