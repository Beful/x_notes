package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.*;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.mapper.RoleDao;
import com.xiaoxin.notes.mapper.RoleMenuDao;
import com.xiaoxin.notes.mapper.RolePermissionDao;
import com.xiaoxin.notes.service.RoleService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 26727
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public void selectRoleCount(String rname) {
        Integer integer = baseMapper.selectCount(new QueryWrapper<RoleEntity>().eq("rname", rname));
        if(integer > 0){
            throw new RunServerException("角色已新增,请更换角色名称！");
        }
    }

    @Override
    public RoleEntity selectById(String id){
        return baseMapper.selectById(id);
    }

    @Override
    public List<Integer> selMenuByRoleId(String id) {
        List<MenuEntity> list = roleMenuDao.selMenuByRoleId(id);
        List<Integer> collect = list.stream().filter(rm -> {
            return !"0".equals(rm.getFathernode());
        }).map(MenuEntity::getMid).collect(Collectors.toList());

        return collect;
    }

    @Override
    public void saveMenusByRoleId(String roleId, String mids) {
        try {
            roleMenuDao.deleteByRoleId(roleId);
            String[] split = mids.split(",");
            List<RoleMenuEntity> list = new ArrayList<>();
            for (String mid : split) {
                RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                roleMenuEntity.setRoleId(Integer.valueOf(roleId));
                roleMenuEntity.setMenuId(Integer.valueOf(mid));
                list.add(roleMenuEntity);
            }
            Integer integer = roleMenuDao.saveMenusByPerId(list);
        }catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> selPerByRoleId(String id) {
        return rolePermissionDao.selPerByRoleId(id);
    }

    @Override
    public void savePersByRoleId(String roleId, String pids) {
        rolePermissionDao.deleteByRoleId(roleId);
        String[] split = pids.split(",");
        List<RolePermissionEntity> list = new ArrayList<>();
        for (String pid : split) {
            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setRoleId(Integer.valueOf(roleId));
            rolePermissionEntity.setPermissionId(Integer.valueOf(pid));
            list.add(rolePermissionEntity);
        }
        rolePermissionDao.savePersByRoleId(list);
    }

    @Override
    public PageUtils selList(String query,int pageSize,int pagenum) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constant.PAGE,String.valueOf(pagenum));
        params.put(Constant.LIMIT,String.valueOf(pageSize));

        IPage<RoleEntity> page = this.page(new QueryPage<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
                        .like(StringUtils.isNotBlank(query), "rname", query)
        );
        return new PageUtils(page);
    }

    @Override
    public void delOneRole(String id) {
        Integer integer = roleMenuDao.selectCount(new QueryWrapper<RoleMenuEntity>().eq("roleId", id));
        if(integer > 0){
            throw new RunServerException("请先把当前角色对应菜单去除，再进行删除角色！");
        }
        Integer integer1 = rolePermissionDao.selectCount(new QueryWrapper<RolePermissionEntity>().eq("roleId", id));
        if(integer1 > 0){
            throw new RunServerException("请先把当前角色对应权限去除，再进行删除角色！");
        }
        int i = baseMapper.deleteById(id);
        if (i == 0) {
            throw new RunServerException("删除权限失败！");
        }
    }

}