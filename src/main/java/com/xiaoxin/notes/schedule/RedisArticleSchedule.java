package com.xiaoxin.notes.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.config.RedisConfig;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.ArticleLike;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.mapper.AriticleDao;
import com.xiaoxin.notes.service.ArticleLikeService;
import com.xiaoxin.notes.service.MenuService;
import com.xiaoxin.notes.service.RedisService;
import com.xiaoxin.notes.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
@Component
@Slf4j
public class RedisArticleSchedule {

    @Autowired
    private ArticleLikeService likeService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AriticleDao ariticleDao;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 更新点赞/评论信息到 Mysql数据库
     *
     * 直接指定时间间隔，例如：一小时
     */
    @Scheduled(fixedRate=1000 * 60 * 60 * 24)
    public void updateRedisLiked() {
        // 同步 t_article_like
        likeService.transLikedFromRedis2DB();
        // 同步 t_article t_comment 点赞数
        likeService.transLikedCountFromRedis2DB();
        // 评论量数据 同步文章表
        likeService.transCommentFromRedis2DB();
        // mysql 同步 redis
        likeService.transArticleCommentDB2Redis();
        // 文章浏览
        redisService.transArticleBrowseRedis2DB();
    }

    /**
     * 更新热点文章到 Redis数据库
     *
     * 直接指定时间间隔，例如：一小时
     */
    @Scheduled(fixedRate=1000 * 60 * 60 * 24)
    public void updateRedisPopularArticle() {
        redisTemplate.delete(RedisConfig.LIST_KEY_POPULAR_ARTICLE);
        List<AriticleEntity> list = ariticleDao.selectList(new QueryWrapper<AriticleEntity>().orderByDesc("browse_num").last("limit 8"));
        for (AriticleEntity article : list) {
            redisTemplate.opsForList().rightPush(RedisConfig.LIST_KEY_POPULAR_ARTICLE, article);
        }

    }

}
