package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.dto.LikedCountDTO;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.ArticleLike;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
public interface RedisService {

    /**
     * 评论点赞。状态为1
     */
    void saveLiked2Redis(String likedArticleId, String likedPostId);

    /**
     * 文章点赞。状态为1
     */
    void saveLikedArticle2Redis(String likedArticleId, String likedPostId);

    /**
     * 评论取消点赞。将状态改变为0
     */
    void unlikeCommentFromRedis(String likedArticleId, String likedPostId);

    /**
     * 文章取消点赞。将状态改变为0
     */
    void unlikeArticleFromRedis(String likedArticleId, String likedPostId);

    /**
     * 从Redis中删除一条评论点赞数据
     */
    void deleteLikedFromRedis(String likedArticleId, String likedPostId);

    /**
     * 从Redis中删除一条文章点赞数据
     */
    void deleteArticleLikedFromRedis(String likedArticleId, String likedPostId);

    /**
     * 该评论的点赞数加1
     */
    void incrementLikedCount(String likedArticleId);

    /**
     * 该文章的点赞数加1
     */
    void incrementLikedArticleCount(String likedArticleId);

    /**
     * 该评论的点赞数减1
     */
    void decrementLikedCount(String likedArticleId);

    /**
     * 该文章的点赞数减1
     */
    void decrementArticleLikedCount(String likedArticleId);

    /**
     * 获取Redis中存储的所有评论点赞数据
     */
    List<ArticleLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有文章点赞数据
     */
    List<ArticleLike> getLikedArticleDataFromRedis();

    /**
     * 获取Redis中存储的所有评论点赞数量
     */
    List<LikedCountDTO> getLikedCountFromRedis();

    /**
     * 获取Redis中存储的所有文章点赞数量
     */
    List<LikedCountDTO> getLikedArticleCountFromRedis();

    /**
     * 查找用户key 评论点赞数量
     */
    Integer getCommentLikedNum(String likedArticleId);

    /**
     * 查找用户key 文章点赞数量
     */
    Integer getArticleLikedNum(String likedArticleId);

    /**
     * 保存文章评论数据到redis
     */
    void saveComment2Redis(String articleId, Integer id);

    /**
     * 保存文章评论数量到redis+1
     */
    void incrementCommentLikedCount(String articleId);

    /**
     * 获取Redis中存储的所有文章评论数量
     */
    List<LikedCountDTO> getArticleCommentCountFromRedis();

    /**
     * 查找用户key 文章评论数量
     */
    Integer getArticleCommentCount(String articleId);

    /**
     * 查找热门文章缓存
     */
    List<AriticleEntity> getPopularArticle();

    List<ArticleLike> getLikedCommentDataNoDelete();

    List<ArticleLike> getLikedArticleDataNoDelete();

    void saveBrowseNum2Redis(String articleId, String userId);

    void transArticleBrowseRedis2DB();

    Integer getArticleBrowseNum(String ariticle_id);
}
