package com.xiaoxin.notes.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2021/1/19.
 *
 * @author XiaoXinZai
 */
@Data
public class AriticleVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    private String title;
    /**
     *
     */
    private String content;
    /**
     * 作者
     */
    private String author;
    /**
     * 发布状态 0->未发布 1->已发布
     */
    private Integer isPublish;

    private String tags;

    private String sorts;

    private String type;

    private String originalLink;

    private String summary;

    private String userId;
}

