package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiaoxin.notes.controller.ex.ParamsErrorException;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.ArticleLike;
import com.xiaoxin.notes.entity.CommentEntity;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.entity.enums.DelStatusEnum;
import com.xiaoxin.notes.mapper.AriticleDao;
import com.xiaoxin.notes.mapper.CommentDao;
import com.xiaoxin.notes.mapper.UserDao;
import com.xiaoxin.notes.service.CommentService;
import com.xiaoxin.notes.service.RedisService;
import com.xiaoxin.notes.utils.IdUtils;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AriticleDao articleDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String query1 = params.get("query").toString();
        IPage<CommentEntity> page = this.page(
                new QueryPage<CommentEntity>().getPage(params),
                new QueryWrapper<CommentEntity>().like(StringUtils.isNotBlank(query1), "article_title", query1).orderByDesc("praise_num")
        );

        return new PageUtils(page);
    }

    @Override
    public void commentArticle(CommentEntity comment) {
        if(StringUtils.isBlank(comment.getContent())){
            throw new ParamsErrorException("输入内容不可以为空！");
        }
        comment.setCreateTime(new Date());
        baseMapper.insert(comment);
        redisService.saveComment2Redis(comment.getArticleId(),comment.getId());
        redisService.incrementCommentLikedCount(comment.getArticleId());
    }

    @Override
    public List<CommentEntity> getCommentInfo(String article_id) {
        List<CommentEntity> commentList = baseMapper.selectList(new QueryWrapper<CommentEntity>().eq("article_id", article_id).orderByDesc("create_time"));

        List<CommentEntity> collect = commentList.stream().filter(commentEntity -> {
            return commentEntity.getStatus() == DelStatusEnum.NODEL && commentEntity.getCommentLevel() == 1;
        }).peek(com->{
            com.setTimeStamp(IdUtils.getTimeStamp(com.getCreateTime()));
            com.setPraiseNum(redisService.getCommentLikedNum(String.valueOf(com.getId())));
            com.setChildren(getChildren(com,commentList));
            com.setUserChild(userDao.selectById(com.getParentCommentUserId()));
        }).sorted(
                Comparator.comparing(CommentEntity::getCreateTime).reversed()
        ).collect(Collectors.toList());

        return collect;
    }

    @Override
    public void updateLikeCommentNumById(String key, Integer likeNum) {
        baseMapper.update(null,
                new UpdateWrapper<CommentEntity>().eq("id",key).set("praise_num",likeNum));
    }

    private List<CommentEntity> getChildren(CommentEntity root, List<CommentEntity> all) {
        List<CommentEntity> childre = all.stream().filter(commentEntity -> {
            return commentEntity.getStatus() == DelStatusEnum.NODEL && commentEntity.getParentCommentId().equals(String.valueOf(root.getId()));
        }).peek(commentEntity -> {
            commentEntity.setTimeStamp(IdUtils.getTimeStamp(commentEntity.getCreateTime()));
            commentEntity.setUserChild(userDao.selectById(commentEntity.getParentCommentUserId()));
            commentEntity.setPraiseNum(redisService.getCommentLikedNum(String.valueOf(commentEntity.getId())));
            commentEntity.setChildren(getChildren(commentEntity, all));
        }).sorted(
            Comparator.comparing(CommentEntity::getCreateTime).reversed()
        ).collect(Collectors.toList());

        return childre;
    }



}