package com.xiaoxin.notes.service.Impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;

import com.xiaoxin.notes.mapper.QuartzDao;
import com.xiaoxin.notes.entity.QuartzEntity;
import com.xiaoxin.notes.service.QuartzService;


@Service("quartzService")
public class QuartzServiceImpl extends ServiceImpl<QuartzDao, QuartzEntity> implements QuartzService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<QuartzEntity> page = this.page(
                new QueryPage<QuartzEntity>().getPage(params),
                new QueryWrapper<QuartzEntity>()
        );
        return new PageUtils(page);
    }

}