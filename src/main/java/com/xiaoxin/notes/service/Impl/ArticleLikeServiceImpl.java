package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxin.notes.config.RedisConfig;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.dto.LikedCountDTO;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.ArticleLike;
import com.xiaoxin.notes.entity.CommentEntity;
import com.xiaoxin.notes.entity.UserEntity;
import com.xiaoxin.notes.enums.LikedStatusEnum;
import com.xiaoxin.notes.mapper.AriticleDao;
import com.xiaoxin.notes.mapper.ArticleLikeDao;
import com.xiaoxin.notes.mapper.CommentDao;
import com.xiaoxin.notes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeDao, ArticleLike> implements ArticleLikeService {

    @Autowired
    RedisService redisService;
    @Autowired
    AriticleDao ariticleDao;
    @Autowired
    AriticleService ariticleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleLikeDao articleLikeDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Page<ArticleLike> getLikedListByLikedUserId(String likedArticleId, Pageable pageable) {
        return baseMapper.findByLikedUserIdAndStatus(likedArticleId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public Page<ArticleLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        return baseMapper.findByLikedPostIdAndStatus(likedPostId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public ArticleLike getByLikedUserIdAndLikedPostId(String likedArticleId, String likedPostId,Integer isArticleComment) {
        return baseMapper.findByLikedUserIdAndLikedPostId(likedArticleId, likedPostId,isArticleComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transLikedFromRedis2DB() {
        List<ArticleLike> list = redisService.getLikedDataFromRedis();
        for(ArticleLike like : list) {
            ArticleLike ul = getByLikedUserIdAndLikedPostId(like.getLikedArticleId(), like.getLikedPostId(),0);
            if(ul == null) {
                //没有记录，直接存入
                like.setIsArticleComment(0);
                save(like);
            } else {
                ul.setIsArticleComment(0);
                //有记录，需要更新
                ul.setStatus(like.getStatus());
                baseMapper.updateById(ul);
            }
        }

        List<ArticleLike> list2 = redisService.getLikedArticleDataFromRedis();
        for(ArticleLike like : list2) {
            ArticleLike ul = getByLikedUserIdAndLikedPostId(like.getLikedArticleId(), like.getLikedPostId(),1);
            if(ul == null){
                //没有记录，直接存入
                like.setIsArticleComment(1);
                save(like);
            }else{
                //有记录，需要更新
                ul.setIsArticleComment(1);
                ul.setStatus(like.getStatus());
                baseMapper.updateById(ul);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        for(LikedCountDTO dto : list) {
            CommentEntity comment = commentService.getById(dto.getKey());
            Integer likeNum = dto.getValue();
            comment.setPraiseNum(likeNum);
            //更新点赞数量
            commentService.updateLikeCommentNumById(dto.getKey(),likeNum);
        }

        List<LikedCountDTO> list2 = redisService.getLikedArticleCountFromRedis();
        for(LikedCountDTO dto : list2) {
            AriticleEntity ariticleEntity = ariticleDao.selectById(dto.getKey());
            Integer likeNum = dto.getValue();
            ariticleEntity.setLikeNum(likeNum);
            //更新点赞数量
            ariticleService.updateLikeNumById(dto.getKey(),likeNum);
        }

    }

    @Override
    public void transCommentFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getArticleCommentCountFromRedis();
        for(LikedCountDTO dto : list) {
            AriticleEntity byId = ariticleService.getById(dto.getKey());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if(byId != null){
                Integer likeNum = dto.getValue();
                byId.setCommentNum(likeNum);
                //更新点赞数量
                commentService.updateLikeCommentNumById(dto.getKey(),likeNum);
            }
        }
    }

    @Override
    public void transArticleCommentDB2Redis() {
        // mysql 同步redis
        List<ArticleLike> articleLikes = baseMapper.selectList(null);

        articleLikes.stream().peek(articleLike->{
            if(articleLike.getIsArticleComment() == 1) {
                // 评论数据
                redisService.saveLiked2Redis(articleLike.getLikedArticleId(), articleLike.getLikedPostId());
            } else if(articleLike.getIsArticleComment() == 0){
                // 文章数据
                redisService.saveLikedArticle2Redis(articleLike.getLikedArticleId(), articleLike.getLikedPostId());
            }
        }).collect(Collectors.toList());

        // 评论点赞量
        List<CommentEntity> list1 = commentDao.selectList(new QueryWrapper<CommentEntity>().eq("status", 1));
        list1.stream().filter(com -> com.getPraiseNum() != 0)
                .peek(com->{ redisTemplate.opsForHash().increment(RedisConfig.MAP_KEY_COMMENT_LIKED_COUNT, String.valueOf(com.getId()), com.getPraiseNum()); })
                .collect(Collectors.toList());

        // 文章点赞量
        List<AriticleEntity> list2 = ariticleDao.selectList(new QueryWrapper<AriticleEntity>().eq("is_del", 0).gt("like_num", 0));
        list2.stream().filter(ariticleEntity -> ariticleEntity.getLikeNum() != 0)
                .peek(ariticleEntity->{redisTemplate.opsForHash().increment(RedisConfig.MAP_KEY_ARTICLE_LIKED_COUNT, String.valueOf(ariticleEntity.getId()),ariticleEntity.getLikeNum()); })
                .collect(Collectors.toList());


        List<AriticleEntity> list = ariticleDao.selectList(new QueryWrapper<AriticleEntity>().eq("is_del", 0).gt("comment_num", 0));
        list.stream().filter(ariticleEntity -> {
           return ariticleEntity.getIsDel() == 0 && ariticleEntity.getCommentNum() > 0;
        }).map(ariticleEntity -> {
            List<CommentEntity> commentEntities = commentDao.selectList(new QueryWrapper<CommentEntity>().eq("article_d", ariticleEntity.getId()).eq("comment_level", 1));
            commentEntities.stream().map(com->{
                // 保存文章评论数据
                redisService.saveComment2Redis(com.getArticleId(), com.getId());
                // 数量
                redisService.incrementCommentLikedCount(com.getArticleId());
                return com;
            }).collect(Collectors.toList());
            return ariticleEntity;
        }).collect(Collectors.toList());


    }
}


