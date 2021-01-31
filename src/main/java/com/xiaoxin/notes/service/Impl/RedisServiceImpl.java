package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiaoxin.notes.config.RedisConfig;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.dto.LikedCountDTO;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.ArticleLike;
import com.xiaoxin.notes.entity.enums.LikedStatusEnum;
import com.xiaoxin.notes.mapper.AriticleDao;
import com.xiaoxin.notes.mapper.ArticleLikeDao;
import com.xiaoxin.notes.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private AriticleDao ariticleDao;
    @Autowired
    private ArticleLikeDao articleLikeDao;

    @Override
    public void saveLiked2Redis(String likedArticleId, String likedPostId) {
        if(StringUtils.isBlank(likedArticleId) || StringUtils.isBlank(likedPostId)){
            throw new RunServerException("出现错误！");
        }
        String key = RedisConfig.getLikedKey(likedArticleId, likedPostId);
        redisTemplate.opsForHash().put(RedisConfig.MAP_KEY_COMMENT_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void saveLikedArticle2Redis(String likedArticleId, String likedPostId) {
        if(StringUtils.isBlank(likedArticleId) || StringUtils.isBlank(likedPostId)){
            throw new RunServerException("出现错误！");
        }
        String key = RedisConfig.getLikedKey(likedArticleId, likedPostId);
        redisTemplate.opsForHash().put(RedisConfig.MAP_KEY_ARTICLE_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeCommentFromRedis(String likedArticleId, String likedPostId) {
        String key = RedisConfig.getLikedKey(likedArticleId, likedPostId);
        redisTemplate.opsForHash().put(RedisConfig.MAP_KEY_COMMENT_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void unlikeArticleFromRedis(String likedArticleId, String likedPostId) {
        String key = RedisConfig.getLikedKey(likedArticleId, likedPostId);
        redisTemplate.opsForHash().put(RedisConfig.MAP_KEY_ARTICLE_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedArticleId, String likedPostId) {
        String key = RedisConfig.getLikedKey(likedArticleId, likedPostId);
        redisTemplate.opsForHash().delete(RedisConfig.MAP_KEY_COMMENT_LIKED, key);
    }

    @Override
    public void deleteArticleLikedFromRedis(String likedArticleId, String likedPostId) {
        String key = RedisConfig.getLikedKey(likedArticleId, likedPostId);
        redisTemplate.opsForHash().delete(RedisConfig.MAP_KEY_ARTICLE_LIKED, key);
    }

    @Override
    public void incrementLikedCount(String likedArticleId) {
        redisTemplate.opsForHash().increment(RedisConfig.MAP_KEY_COMMENT_LIKED_COUNT, likedArticleId, 1);
    }

    @Override
    public void incrementLikedArticleCount(String likedArticleId) {
        redisTemplate.opsForHash().increment(RedisConfig.MAP_KEY_ARTICLE_LIKED_COUNT, likedArticleId, 1);
    }

    @Override
    public void decrementLikedCount(String likedArticleId) {
        redisTemplate.opsForHash().increment(RedisConfig.MAP_KEY_COMMENT_LIKED_COUNT, likedArticleId, -1);
    }

    @Override
    public void decrementArticleLikedCount(String likedArticleId) {
        redisTemplate.opsForHash().increment(RedisConfig.MAP_KEY_ARTICLE_LIKED_COUNT, likedArticleId, -1);
    }

    @Override
    public List<ArticleLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConfig.MAP_KEY_COMMENT_LIKED, ScanOptions.NONE);
        List<ArticleLike> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedArticleId，likedPostId
            String[] split = key.split("::");
            String likedArticleId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();
            //组装成 ArticleLike 对象
            ArticleLike articleLike = new ArticleLike(likedArticleId, likedPostId, value,1);
            list.add(articleLike);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisConfig.MAP_KEY_COMMENT_LIKED, key);
        }
        return list;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConfig.MAP_KEY_COMMENT_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisConfig.MAP_KEY_COMMENT_LIKED_COUNT, key);
        }
        return list;
    }

    @Override
    public Integer getCommentLikedNum(String likedArticleId) {
        return (Integer) redisTemplate.opsForHash().get(RedisConfig.MAP_KEY_COMMENT_LIKED_COUNT,likedArticleId);
    }

    @Override
    public Integer getArticleLikedNum(String likedArticleId) {
        return (Integer) redisTemplate.opsForHash().get(RedisConfig.MAP_KEY_ARTICLE_LIKED_COUNT,likedArticleId);
    }

    @Override
    public void saveComment2Redis(String articleId, Integer id) {
        if(StringUtils.isBlank(articleId)){
            throw new RunServerException("出现错误！");
        }
        String key = RedisConfig.getLikedKey(articleId, String.valueOf(id));
        redisTemplate.opsForHash().put(RedisConfig.MAP_KEY_ARTICLE_COMMENT, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void incrementCommentLikedCount(String articleId) {
        redisTemplate.opsForHash().increment(RedisConfig.MAP_KEY_ARTICLE_COMMENT_COUNT, articleId, 1);
    }

    @Override
    public List<LikedCountDTO> getArticleCommentCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConfig.MAP_KEY_ARTICLE_COMMENT_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisConfig.MAP_KEY_ARTICLE_COMMENT_COUNT, key);
        }
        return list;
    }

    @Override
    public Integer getArticleCommentCount(String ariticle_id) {
        return (Integer) redisTemplate.opsForHash().get(RedisConfig.MAP_KEY_ARTICLE_COMMENT_COUNT, ariticle_id);
    }

    @Override
    public List<AriticleEntity> getPopularArticle() {
        return redisTemplate.opsForList().range(RedisConfig.LIST_KEY_POPULAR_ARTICLE,0,-1);
    }

    @Override
    public List<ArticleLike> getLikedCommentDataNoDelete() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConfig.MAP_KEY_COMMENT_LIKED, ScanOptions.NONE);
        List<ArticleLike> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedArticleId，likedPostId
            String[] split = key.split("::");
            String likedArticleId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();
            //组装成 ArticleLike 对象
            ArticleLike articleLike = new ArticleLike(likedArticleId, likedPostId, value,1);
            list.add(articleLike);
        }
        return list;
    }

    @Override
    public List<ArticleLike> getLikedArticleDataNoDelete() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConfig.MAP_KEY_ARTICLE_LIKED, ScanOptions.NONE);
        List<ArticleLike> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedArticleId，likedPostId
            String[] split = key.split("::");
            String likedArticleId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();
            //组装成 ArticleLike 对象
            ArticleLike articleLike = new ArticleLike(likedArticleId, likedPostId, value,0);
            list.add(articleLike);
        }
        return list;
    }

    @Override
    public void saveBrowseNum2Redis(String articleId, String userId) {
        if(StringUtils.isBlank(articleId) || StringUtils.isBlank(userId)){
            throw new RunServerException("出现错误！");
        }
        if(!redisTemplate.opsForHash().hasKey(RedisConfig.MAP_KEY_ARTICLE_USER,articleId)){
            redisTemplate.opsForHash().put(RedisConfig.MAP_KEY_ARTICLE_USER, articleId, userId);
            redisTemplate.opsForHash().increment(RedisConfig.MAP_KEY_ARTICLE_USER_COUNT, articleId, 1);
        }
    }

    @Override
    public void transArticleBrowseRedis2DB() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConfig.MAP_KEY_ARTICLE_USER_COUNT, ScanOptions.NONE);
        while(cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer value = (Integer) entry.getValue();

            ariticleDao.update(null,new UpdateWrapper<AriticleEntity>()
                    .eq("id",key).set("browse_num",value));
        }
    }

    @Override
    public Integer getArticleBrowseNum(String articleId) {
        return (Integer) redisTemplate.opsForHash().get(RedisConfig.MAP_KEY_ARTICLE_USER_COUNT,articleId);
    }

    @Override
    public List<ArticleLike> getLikedArticleDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConfig.MAP_KEY_ARTICLE_LIKED, ScanOptions.NONE);
        List<ArticleLike> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedArticleId，likedPostId
            String[] split = key.split("::");
            String likedArticleId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();
            //组装成 ArticleLike 对象
            ArticleLike articleLike = new ArticleLike(likedArticleId, likedPostId, value,0);
            list.add(articleLike);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisConfig.MAP_KEY_ARTICLE_LIKED, key);
        }
        return list;
    }

    @Override
    public List<LikedCountDTO> getLikedArticleCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConfig.MAP_KEY_ARTICLE_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisConfig.MAP_KEY_ARTICLE_LIKED_COUNT, key);
        }
        return list;
    }

}
