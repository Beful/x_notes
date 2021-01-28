package com.xiaoxin.notes.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.CommentEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 文章评论表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 10:58:34
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void commentArticle(CommentEntity comment);

    List<CommentEntity> getCommentInfo(String ariticle_id);

    void updateLikeCommentNumById(String key, Integer likeNum);
}

