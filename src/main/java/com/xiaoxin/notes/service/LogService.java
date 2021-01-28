package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.LogEntity;
import com.xiaoxin.notes.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 *
 * @date 2021-01-12 20:18:07
 */
public interface LogService extends IService<LogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

