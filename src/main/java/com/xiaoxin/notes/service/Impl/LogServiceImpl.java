package com.xiaoxin.notes.service.Impl;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import com.xiaoxin.notes.entity.LogEntity;
import com.xiaoxin.notes.mapper.LogDao;
import com.xiaoxin.notes.service.LogService;
import com.xiaoxin.notes.utils.IPUtils;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;


@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogDao, LogEntity> implements LogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogEntity> page = this.page(
                new QueryPage<LogEntity>().getPage(params),
                new QueryWrapper<LogEntity>().orderByDesc("create_time")
        );

        return new PageUtils(page);
    }

}