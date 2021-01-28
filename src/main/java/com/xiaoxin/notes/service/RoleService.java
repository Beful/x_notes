package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.RoleEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.List;

/**
 * 
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
public interface RoleService extends IService<RoleEntity> {

    void selectRoleCount(String rname);

    RoleEntity selectById(String id);

    List<Integer> selMenuByRoleId(String id);

    void saveMenusByRoleId(String roleId, String mids);

    List<Integer> selPerByRoleId(String id);

    void savePersByRoleId(String roleId, String pids);

    PageUtils selList(String query,int pageSize,int pagenum);

    void delOneRole(String id);
}

