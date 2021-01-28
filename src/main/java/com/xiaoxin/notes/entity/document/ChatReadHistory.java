package com.xiaoxin.notes.entity.document;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created on 2021/1/27.
 *
 * @author XiaoXinZai
 */
@Data
@Document
public class ChatReadHistory {

    @Id
    private String id;
    private String name;
    private String content;
    @Indexed
    private int dateId;
    @Indexed
    private int monthId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
