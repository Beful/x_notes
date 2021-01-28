package com.xiaoxin.notes.service.Impl;

import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.AriticleSortRelationEntity;
import com.xiaoxin.notes.mapper.AriticleSortRelationDao;
import com.xiaoxin.notes.service.AriticleSortRelationService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("ariticleSortRelationService")
public class AriticleSortRelationServiceImpl extends ServiceImpl<AriticleSortRelationDao, AriticleSortRelationEntity> implements AriticleSortRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AriticleSortRelationEntity> page = this.page(
                new QueryPage<AriticleSortRelationEntity>().getPage(params),
                new QueryWrapper<AriticleSortRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<AriticleEntity> selKnowLearn() {
        return baseMapper.selKnowLearn();
    }

}