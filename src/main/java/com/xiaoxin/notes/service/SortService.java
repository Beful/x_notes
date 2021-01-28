package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.SortEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 12:26:22
 */
public interface SortService extends IService<SortEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

