package com.xiaoxin.notes.service.Impl;

import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.mapper.LeaverBoardDao;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xiaoxin.notes.entity.LeaverBoardEntity;
import com.xiaoxin.notes.service.LeaverBoardService;


@Service("leaverBoardService")
public class LeaverBoardServiceImpl extends ServiceImpl<LeaverBoardDao, LeaverBoardEntity> implements LeaverBoardService {

    @Override
    public Integer selByUserIdCount(Integer userid) {
        return baseMapper.selectCount(new QueryWrapper<LeaverBoardEntity>().eq("userid",userid));
    }

    @Override
    public List<LeaverBoardEntity> selList() {
        List<LeaverBoardEntity> list = baseMapper.selectList(new QueryWrapper<LeaverBoardEntity>().eq("is_del", 0).orderByDesc("create_time"));

        List<LeaverBoardEntity> collect = list.stream()
                .filter(lea-> lea.getCommentLevel() == 1 && lea.getIsDel() == 0)
                .peek(lea -> lea.setChildren(getChildren(lea,list)))
                .sorted(Comparator.comparing(LeaverBoardEntity::getCreateTime).reversed())
                .collect(Collectors.toList());

        return collect;
    }

    private List<LeaverBoardEntity> getChildren(LeaverBoardEntity root, List<LeaverBoardEntity> all) {
        List<LeaverBoardEntity> collect = all.stream()
                .filter(lea-> lea.getCommentLevel().equals(root.getId()) && lea.getIsDel() == 0)
                .peek(lea -> lea.setChildren(getChildren(lea, all)))
                .sorted(Comparator.comparing(LeaverBoardEntity::getCreateTime).reversed())
                .collect(Collectors.toList());

        return collect;
    }

}