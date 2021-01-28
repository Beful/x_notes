package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.dto.LikedCountDTO;
import com.xiaoxin.notes.entity.AriticleSortRelationEntity;
import com.xiaoxin.notes.entity.ArticleLike;
import io.swagger.models.auth.In;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
public interface ArticleLikeService extends IService<ArticleLike> {
    /**
     * 根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
     */
    Page<ArticleLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable);
    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     */
    Page<ArticleLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable);
    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     */
    ArticleLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId, Integer isArticleComment);
    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();
    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();

    /**
     * 将Redis中的评论数量数据存入数据库
     */
    void transCommentFromRedis2DB();

    void transArticleCommentDB2Redis();
}
