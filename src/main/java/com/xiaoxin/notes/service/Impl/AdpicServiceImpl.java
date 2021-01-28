package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.AdpicEntity;
import com.xiaoxin.notes.mapper.AdpicDao;
import com.xiaoxin.notes.service.AdpicService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("adpicService")
public class AdpicServiceImpl extends ServiceImpl<AdpicDao, AdpicEntity> implements AdpicService {

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdpicEntity> page = this.page(
                new QueryPage<AdpicEntity>().getPage(params),
                new QueryWrapper<AdpicEntity>()
        );

        List<AdpicEntity> list = page.getRecords();
        list.stream().filter(adpic -> {
            String img = adpic.getImg();
            adpic.setImg(ENDPOINT + "/" + BUCKET_NAME + "/" + img);
            return adpic.getId() != null;
        }).peek(adpic -> {
            List<String> adList = new ArrayList<>();
            String img = adpic.getImg();
            adList.add(img);
            adpic.setPhoto(adList);
        }).collect(Collectors.toList());

        return new PageUtils(page);
    }

    @Override
    public List<AdpicEntity> imageBox() {
        QueryWrapper<AdpicEntity> qw = new QueryWrapper<AdpicEntity>()
                .eq("model", Constant.PICTURE_HOME);
        List<AdpicEntity> list = baseMapper.selectList(qw);

        List<AdpicEntity> collect = list.stream().filter(adpic -> {
            adpic.setImg(ENDPOINT + "/" + BUCKET_NAME + "/" + adpic.getImg());
            return adpic.getId() != null;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<AdpicEntity> adpicBox() {
        QueryWrapper<AdpicEntity> qw = new QueryWrapper<AdpicEntity>()
                .eq("model", Constant.PICTURE_ADPIC);
        List<AdpicEntity> list = baseMapper.selectList(qw);

        List<AdpicEntity> collect = list.stream().filter(adpic -> {
            adpic.setImg(ENDPOINT + "/" + BUCKET_NAME + "/" + adpic.getImg());
            return adpic.getId() != null;
        }).collect(Collectors.toList());

        return collect;
    }

}