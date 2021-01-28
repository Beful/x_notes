package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.vo.AriticleVo;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 文章表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
public interface AriticleService extends IService<AriticleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Map<String,Object> sortTagList();

    void saveAriticle(AriticleVo ari);

    Map<String,Object> indexList();

    void updateAriticle(AriticleVo ari);

    AriticleEntity getAriticleCoById(Integer id);

    AriticleEntity ArticleInfo(String user_id, String ariticle_id);

    void updateLikeNumById(String id, Integer likeNum);
}

