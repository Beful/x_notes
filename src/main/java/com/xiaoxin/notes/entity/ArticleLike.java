package com.xiaoxin.notes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoxin.notes.enums.LikedStatusEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created on 2021/1/21.
 *
 * @author XiaoXinZai
 */
@Data
@TableName("t_article_like")
public class ArticleLike {
    //主键id
    @TableId(type = IdType.AUTO)
    private Integer id;
    //被点赞的用户的id
    private String likedArticleId;
    //点赞的用户的id
    private String likedPostId;
    //点赞的状态.默认未点赞
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    private Integer isArticleComment;


    ArticleLike() {}
    public ArticleLike(String likedArticleId, String likedPostId, Integer status,Integer isArticleComment) {
        this.likedArticleId = likedArticleId;
        this.likedPostId = likedPostId;
        this.status = status;
    }

}
