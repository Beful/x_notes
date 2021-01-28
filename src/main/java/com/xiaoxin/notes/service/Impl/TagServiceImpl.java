package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiaoxin.notes.entity.TagEntity;
import com.xiaoxin.notes.mapper.TagDao;
import com.xiaoxin.notes.service.TagService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String query1 = params.get("query").toString();
        IPage<TagEntity> page = this.page(
                new QueryPage<TagEntity>().getPage(params),
                new QueryWrapper<TagEntity>().like(StringUtils.isNotBlank(query1), "name", query1)
        );

        return new PageUtils(page);
    }

}