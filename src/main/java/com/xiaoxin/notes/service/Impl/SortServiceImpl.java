package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiaoxin.notes.entity.SortEntity;
import com.xiaoxin.notes.mapper.SortDao;
import com.xiaoxin.notes.service.SortService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("sortService")
public class SortServiceImpl extends ServiceImpl<SortDao, SortEntity> implements SortService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String query1 = params.get("query").toString();
        IPage<SortEntity> page = this.page(
                new QueryPage<SortEntity>().getPage(params),
                new QueryWrapper<SortEntity>().like(StringUtils.isNotBlank(query1), "name", query1)
        );
        return new PageUtils(page);
    }

}