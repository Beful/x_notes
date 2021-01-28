package com.xiaoxin.notes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoxin.notes.entity.ArticleLike;
import com.xiaoxin.notes.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.awt.print.Pageable;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
@Mapper
public interface ArticleLikeDao  extends BaseMapper<ArticleLike> {

    @Select("select * from t_article_like where liked_article_id = #{liked_article_id} && liked_post_id = #{likedPostId} and is_article_comment = #{isArticleComment}")
    ArticleLike findByLikedUserIdAndLikedPostId(@Param("liked_article_id") String likedArticleId,@Param("likedPostId") String likedPostId,@Param("isArticleComment") Integer isArticleComment);

    @Select("select * from t_article_like where liked_article_id = #{liked_article_id} && status = 1")
    Page<ArticleLike> findByLikedUserIdAndStatus(@Param("liked_article_id") String likedArticleId, Integer code, Pageable pageable);

    @Select("select * from t_article_like where liked_post_id = #{likedPostId} and status = 1")
    Page<ArticleLike> findByLikedPostIdAndStatus(@Param("likedPostId") String likedPostId, Integer code, Pageable pageable);
}
