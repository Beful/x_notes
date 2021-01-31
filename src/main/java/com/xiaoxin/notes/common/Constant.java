package com.xiaoxin.notes.common;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Constant
 *
 * @author
 */
public class Constant {
    /**当前页码*/
    public static final String PAGE = "page";
    /**每页显示记录数*/
    public static final String LIMIT = "limit";
    /**排序字段*/
    public static final String ORDER_FIELD = "sidx";
    /**排序方式*/
    public static final String ORDER = "order";
    /**升序*/
    public static final String ASC = "asc";
    /**注册用户默认权限id*/
    public static final Integer ROLEID = 7;
    /**Menu菜单默认父id*/
    public static final String MENUFATHERID = "0";
    /** 图片对应模块名 home */
    public static final String PICTURE_HOME = "home";
    /** 图片对应模块名 adpic */
    public static final String PICTURE_ADPIC = "adpic";
}
