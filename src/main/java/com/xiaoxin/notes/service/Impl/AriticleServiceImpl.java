package com.xiaoxin.notes.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiaoxin.notes.controller.ex.NotFoundException;
import com.xiaoxin.notes.controller.ex.ParamsErrorException;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.entity.*;
import com.xiaoxin.notes.entity.vo.AriticleVo;
import com.xiaoxin.notes.mapper.*;
import com.xiaoxin.notes.service.AriticleService;
import com.xiaoxin.notes.service.RedisService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.QueryPage;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;


@Service("ariticleService")
public class AriticleServiceImpl extends ServiceImpl<AriticleDao, AriticleEntity> implements AriticleService {

    @Autowired
    private SortDao sortDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private AriticleSortRelationDao asrDao;
    @Autowired
    private AriticleTagRelationDao atrDao;
    @Autowired
    RedisService redisService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AriticleEntity> page = this.page(
                new QueryPage<AriticleEntity>().getPage(params),
                new QueryWrapper<AriticleEntity>().orderByDesc("publish_time")
        );

        List<AriticleEntity> collect = page.getRecords().stream()
                .filter(ariticle -> {
                    List<TagEntity> tagEntities = tagDao.selTagsByAriticle(ariticle.getId());
                    ariticle.setTagList(tagEntities);
                    List<Integer> collect2 = tagEntities.stream().map(TagEntity::getId).collect(Collectors.toList());
                    ariticle.setTags(collect2);
                    List<SortEntity> sortList = asrDao.selSortListByAid(ariticle.getId());
                    ariticle.setSortList(sortList);
                    List<Integer> collect1 = sortList.stream().map(SortEntity::getId).collect(Collectors.toList());
                    ariticle.setSorts(collect1);
                    return ariticle.getIsPublish() == 1 && ariticle.getIsDel() == 0;})
                .sorted(Comparator.comparing(AriticleEntity::getPublishTime).reversed())
                .collect(Collectors.toList());

        page.setRecords(collect);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> sortTagList() {
        List<SortEntity> sortEntities = sortDao.selectList(null);
        List<TagEntity> tagEntities = tagDao.selectList(null);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sortList",sortEntities);
        map.put("tagList",tagEntities);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAriticle(AriticleVo ari) {
        try {
            saveAndUpdateAriticle(ari);
        }catch (Exception e){
            e.printStackTrace();
            throw new RunServerException("系统异常！");
        }
    }

    @Override
    public Map<String, Object> indexList() {
        HashMap<String, Object> map = new HashMap<>();

        List<AriticleEntity> ariticleList = baseMapper.selectList(null);
        List<AriticleEntity> collect = ariticleList.stream().filter(ariticle -> {
            List<TagEntity> tagEntities = tagDao.selTagsByAriticle(ariticle.getId());
            ariticle.setTagList(tagEntities);
            List<Integer> collect2 = tagEntities.stream().map(TagEntity::getId).collect(Collectors.toList());
            ariticle.setTags(collect2);
            List<SortEntity> sortList = asrDao.selSortListByAid(ariticle.getId());
            ariticle.setSortList(sortList);
            List<Integer> collect1 = sortList.stream().map(SortEntity::getId).collect(Collectors.toList());
            ariticle.setSorts(collect1);
            return ariticle.getIsPublish() == 1 && ariticle.getIsDel() == 0;
        }).sorted(
            Comparator.comparing(AriticleEntity::getPublishTime).reversed()
        ).collect(Collectors.toList());

        map.put("ariticleList",collect);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAriticle(AriticleVo ari) {
        try {
            baseMapper.deleteById(ari.getId());
            atrDao.delete(new QueryWrapper<AriticleTagRelationEntity>().eq("ariticle_id",ari.getId()));
            asrDao.delete(new QueryWrapper<AriticleSortRelationEntity>().eq("ariticle_id",ari.getId()));

            saveAndUpdateAriticle(ari);
        }catch (Exception e){
            e.printStackTrace();
            throw new RunServerException("系统异常！");
        }
    }

    @Override
    public AriticleEntity getAriticleCoById(Integer id) {
        AriticleEntity ariticle = baseMapper.selectById(id);

        if (ariticle.getIsDel() != 0 || ariticle.getIsPublish() != 1) {
            throw new ParamsErrorException("参数出现错误，400！");
        }

        List<TagEntity> tagEntities = tagDao.selTagsByAriticle(ariticle.getId());
        ariticle.setTagList(tagEntities);

        List<Integer> collect2 = tagEntities.stream().map(TagEntity::getId).collect(Collectors.toList());
        ariticle.setTags(collect2);

        List<SortEntity> sortList = asrDao.selSortListByAid(ariticle.getId());
        ariticle.setSortList(sortList);

        List<Integer> collect1 = sortList.stream().map(SortEntity::getId).collect(Collectors.toList());
        ariticle.setSorts(collect1);

        return ariticle;
    }

    @Override
    public AriticleEntity ArticleInfo(String user_id, String ariticle_id) {
        AriticleEntity ariticle = baseMapper.selectById(ariticle_id);
        if(!user_id.equals(ariticle.getUserId())){
            throw new NotFoundException("url出现错误，404！");
        }
        if (ariticle.getIsDel() != 0 || ariticle.getIsPublish() != 1) {
            throw new ParamsErrorException("参数出现错误，400！");
        }

        List<TagEntity> tagEntities = tagDao.selTagsByAriticle(ariticle.getId());
        ariticle.setTagList(tagEntities);
        ariticle.setTags(tagEntities.stream().map(TagEntity::getId).collect(Collectors.toList()));

        List<SortEntity> sortList = asrDao.selSortListByAid(ariticle.getId());
        ariticle.setSortList(sortList);
        ariticle.setSorts(sortList.stream().map(SortEntity::getId).collect(Collectors.toList()));

        ariticle.setBrowseNum(redisService.getArticleBrowseNum(ariticle_id));
        ariticle.setLikeNum(redisService.getArticleLikedNum(ariticle_id));
        ariticle.setCommentNum(redisService.getArticleCommentCount(ariticle_id));

        return ariticle;
    }

    @Override
    public void updateLikeNumById(String id, Integer likeNum) {
        baseMapper.update(null,
                new UpdateWrapper<AriticleEntity>().eq("id",id).set("like_num",likeNum));
    }

    /**
     * 保存或暂存文章
     * @param ari
     */
    private void saveAndUpdateAriticle(AriticleVo ari) {
        if(StringUtils.isBlank(ari.getTags()) || StringUtils.isBlank(ari.getSorts())){
            throw new RunServerException("不可以不选标签或分类");
        }
        AriticleEntity ariticle = new AriticleEntity();
        ariticle.setTitle(ari.getTitle());
        ariticle.setContent(ari.getContent());
        ariticle.setAuthor(ari.getAuthor());
        ariticle.setCreateTime(new Date());
        ariticle.setType(ari.getType());
        ariticle.setOriginalLink(ari.getOriginalLink());
        ariticle.setSummary(ari.getSummary());
        ariticle.setUserId(ari.getUserId());
        if (ari.getIsPublish() == 1) {
            ariticle.setPublishTime(new Date());
            ariticle.setIsPublish(1);
        } else {
            ariticle.setIsPublish(0);
        }
        baseMapper.insert(ariticle);

        String[] sorts = ari.getSorts().split(",");
        Integer id = ariticle.getId();
        List<AriticleSortRelationEntity> sortList = new ArrayList<>();
        for (String sort : sorts) {
            AriticleSortRelationEntity a = new AriticleSortRelationEntity();
            a.setAriticleId(id);
            a.setSortId(Integer.valueOf(sort));
            sortList.add(a);
        }
        asrDao.saveSortByAri(sortList);

        String[] tags = ari.getTags().split(",");
        List<AriticleTagRelationEntity> tagList = new ArrayList<>();
        for (String tag : tags) {
            AriticleTagRelationEntity b = new AriticleTagRelationEntity();
            b.setAriticleId(id);
            b.setTagId(Integer.valueOf(tag));
            tagList.add(b);
        }
        atrDao.saveTagByAri(tagList);
    }

}