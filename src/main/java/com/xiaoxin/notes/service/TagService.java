package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.TagEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.Map;

/**
 * 标签表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 10:58:34
 */
public interface TagService extends IService<TagEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

