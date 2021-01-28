package com.xiaoxin.notes.service.Impl;

import com.xiaoxin.notes.entity.AriticleTagRelationEntity;
import com.xiaoxin.notes.mapper.AriticleTagRelationDao;
import com.xiaoxin.notes.service.AriticleTagRelationService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("ariticleTagRelationService")
public class AriticleTagRelationServiceImpl extends ServiceImpl<AriticleTagRelationDao, AriticleTagRelationEntity> implements AriticleTagRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AriticleTagRelationEntity> page = this.page(
                new QueryPage<AriticleTagRelationEntity>().getPage(params),
                new QueryWrapper<AriticleTagRelationEntity>()
        );

        return new PageUtils(page);
    }

}