package com.xiaoxin.notes.config;

/**
 * Created on 2021/1/17.
 *
 * @author XiaoXinZai
 */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.MenuEntity;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Redis配置类
 * Created by macro on 2020/3/2.
 */
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    // 评论 - 用户 点赞数据
    public static final String MAP_KEY_COMMENT_LIKED = "MAP_COMMENT_LIKED";
    // 评论 - 点赞数量
    public static final String MAP_KEY_COMMENT_LIKED_COUNT = "MAP_COMMENT_LIKED_COUNT";
    // 文章 - 用户 点赞数据
    public static final String MAP_KEY_ARTICLE_LIKED = "MAP_ARTICLE_LIKED";
    // 文章 - 点赞数量
    public static final String MAP_KEY_ARTICLE_LIKED_COUNT = "MAP_ARTICLE_LIKED_COUNT";
    // 文章评论 - 数据
    public static final String MAP_KEY_ARTICLE_COMMENT = "MAP_ARTICLE_COMMENT";
    // 文章评论 - 数量
    public static final String MAP_KEY_ARTICLE_COMMENT_COUNT = "MAP_ARTICLE_COMMENT_COUNT";
    // 文章 - 用户 浏览数据
    public static final String MAP_KEY_ARTICLE_USER = "MAP_ARTICLE_USER";
    // 文章 - 浏览量
     public static final String MAP_KEY_ARTICLE_USER_COUNT = "MAP_ARTICLE_USER_COUNT";
    // 菜单列表
    public static final String LIST_KEY_MENULIST = "LIST_MENULIST";
    // 热门文章
    public static final String LIST_KEY_POPULAR_ARTICLE = "LIST_POPULAR_ARTICLE";
    // 聊天室userId
    public static final String MAP_KEY_CHAT_USER = "MAP_CHAT_USER";
    // 聊天室userId Count
    public static final String STRING_KEY_CHAT_USER_COUNT = "STRING_CHAT_USER_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param likedUserId 被点赞的人id
     * @param likedPostId 点赞的人的id
     * @return
     */
    public static String getLikedKey(String likedUserId, String likedPostId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(likedPostId);
        return builder.toString();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<Object> serializer = redisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置Redis缓存有效期为1天
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer())).entryTtl(Duration.ofDays(1));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }


}