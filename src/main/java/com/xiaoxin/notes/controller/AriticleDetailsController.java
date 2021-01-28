package com.xiaoxin.notes.controller;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.*;
import com.xiaoxin.notes.service.AriticleService;
import com.xiaoxin.notes.service.AriticleSortRelationService;
import com.xiaoxin.notes.service.CommentService;
import com.xiaoxin.notes.service.RedisService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2021/1/20.
 *
 * @author XiaoXinZai
 */
@RestController
@RequestMapping("ariticle/details")
public class AriticleDetailsController {

    @Autowired
    private AriticleService ariticleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AriticleSortRelationService ariticleSortRelationService;

    /**
     * 文章
     */
    @GetMapping("{user_id}/{ariticle_id}")
    public R ArticleInfo(@PathVariable String user_id, @PathVariable String ariticle_id) {
        Map<String, Object> map = new HashMap<>();
        redisService.saveBrowseNum2Redis(ariticle_id,user_id);
        AriticleEntity ariticleEntity = ariticleService.ArticleInfo(user_id, ariticle_id);
        List<CommentEntity> commentInfo = commentService.getCommentInfo(ariticle_id);
        map.put("article",ariticleEntity);
        map.put("commentInfo",commentInfo);
        return R.ok(map);
    }

    /**
     * 保存评论
     */
    @PostMapping("commentArticle")
    public R commentArticle(@RequestBody CommentEntity comment) {
        commentService.commentArticle(comment);
        return R.ok();
    }

    /**
     * 点赞
     */
    @PostMapping("like")
    public R saveLiked2Redis(@RequestBody ArticleLike like) {
        String msg;
        if(like.getIsArticleComment() == 1) {
                // 评论点赞数据
                List<ArticleLike> likedDataFromRedis = redisService.getLikedCommentDataNoDelete();
                long count = likedDataFromRedis.stream().filter(like1 -> like1.getLikedArticleId().equals(like.getLikedArticleId()) && like1.getLikedPostId().equals(like.getLikedPostId())).count();
                if (count > 0) {
                    // 取消点赞。将状态改变为0
                    redisService.unlikeCommentFromRedis(like.getLikedArticleId(), like.getLikedPostId());
                    // 该用户的点赞数减1
                    redisService.decrementLikedCount(like.getLikedArticleId());
                    // 从Redis中删除一条点赞数据
                    redisService.deleteLikedFromRedis(like.getLikedArticleId(), like.getLikedPostId());
                    msg = "取消点赞成功！";
                } else {
                    // 点赞数据。状态为1
                    redisService.saveLiked2Redis(like.getLikedArticleId(), like.getLikedPostId());
                    // 数量记录 count + 1
                    redisService.incrementLikedCount(like.getLikedArticleId());
                    msg = "点赞成功！";
                }
            } else {
                // 文章点赞数据
                List<ArticleLike> likedArticleDataFromRedis = redisService.getLikedArticleDataNoDelete();
                long count = likedArticleDataFromRedis.stream().filter(like1 -> like1.getLikedArticleId().equals(like.getLikedArticleId()) && like1.getLikedPostId().equals(like.getLikedPostId())).count();
                if (count > 0) {
                    // 取消点赞。将状态改变为0
                    redisService.unlikeArticleFromRedis(like.getLikedArticleId(), like.getLikedPostId());
                    // 该用户的点赞数减1
                    redisService.decrementArticleLikedCount(like.getLikedArticleId());
                    // 从Redis中删除一条点赞数据
                    redisService.deleteArticleLikedFromRedis(like.getLikedArticleId(), like.getLikedPostId());
                    msg = "取消点赞成功！";
                } else {
                    // 点赞数据。状态为1
                    redisService.saveLikedArticle2Redis(like.getLikedArticleId(), like.getLikedPostId());
                    // 数量记录 count + 1
                    redisService.incrementLikedArticleCount(like.getLikedArticleId());
                    msg = "点赞成功！";
                }
            }

        return R.ok(msg);
    }

    /**
     * 首页热门文章
     */
    @GetMapping("/popularArticle")
    public R popularArticle(){
        List<AriticleEntity> popularArticle = redisService.getPopularArticle();
        return R.ok(popularArticle);
    }

    /**
     * 首页学习列表
     */
    @GetMapping("/knowLearn")
    public R knowLearn(){
        List<AriticleEntity> knowLearn = ariticleSortRelationService.selKnowLearn();
        return R.ok(knowLearn);
    }

    /**
     * 首页列表
     */
    @GetMapping("/indexList")
    public R indexList(){
        Map<String, Object> map = ariticleService.indexList();
        return R.ok(map);
    }




}
