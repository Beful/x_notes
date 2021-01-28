package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.PermissionEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.Map;

/**
 * 用户权限表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
public interface PermissionService extends IService<PermissionEntity> {

    
    void insertOnePer(PermissionEntity permission);

    PermissionEntity selOnePer(String id);

    PageUtils selList(Map<String, Object> params);
}

