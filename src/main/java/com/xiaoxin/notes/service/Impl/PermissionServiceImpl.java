package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xiaoxin.notes.entity.PermissionEntity;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.mapper.PermissionDao;
import com.xiaoxin.notes.service.PermissionService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, PermissionEntity> implements PermissionService {

    @Override
    public void insertOnePer(PermissionEntity permission) {
        Integer integer = baseMapper.selectCount(new QueryWrapper<PermissionEntity>().eq("pname", permission.getPname()));
        if(integer > 0){
            throw new RunServerException("权限已新增，请重新添加权限！");
        }
        int insert = baseMapper.insert(permission);
        if (insert == 0){
            throw new RunServerException("添加权限失败！！");
        }
    }

    @Override
    public PermissionEntity selOnePer(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public PageUtils selList(Map<String, Object> params) {

        String query = params.get("query").toString();
        IPage<PermissionEntity> page = this.page(new QueryPage<PermissionEntity>().getPage(params),
                new QueryWrapper<PermissionEntity>()
                        .like(StringUtils.isNotBlank(query),"pname", query)
        );
        return new PageUtils(page);
    }


}