package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.QuartzEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.Map;

/**
 * 定时任务
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-18 20:58:32
 */
public interface QuartzService extends IService<QuartzEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

