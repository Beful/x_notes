package com.xiaoxin.notes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.AdpicEntity;
import com.xiaoxin.notes.service.AdpicService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @date 2021-01-13 14:01:44
 */
@Api(tags = "AdpicController", description = "广告管理")
@RestController
@RequestMapping("notes/adpic")
public class AdpicController {

    @Autowired
    private AdpicService adpicService;

    @GetMapping("list")
    public R list(@RequestParam int pagenum,@RequestParam int pageSize){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constant.PAGE,pagenum);
        params.put(Constant.LIMIT,pageSize);
        PageUtils pageUtils = adpicService.queryPage(params);
        return R.ok(pageUtils);
    }

    /**
     * 信息
     */
    @GetMapping("selOnePic/{id}")
    public R info(@PathVariable("id") Integer id){
        AdpicEntity log = adpicService.getById(id);
        return R.ok(log);
    }

    /**
     * 保存
     */
    @PostMapping("save")
    public R save(@RequestBody AdpicEntity adpic){
        adpicService.save(adpic);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("update")
    public R update(@RequestBody AdpicEntity log){
        adpicService.updateById(log);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable("id") String id){
        adpicService.removeById(id);
        return R.ok();
    }

    /**
     * home 顶部轮播图
     */
    @GetMapping("imageBox")
    public R imageBox(){
        List<AdpicEntity> list = adpicService.imageBox();
        return R.ok(list);
    }

    /**
     * 外部首页 顶部轮播图
     */
    @GetMapping("adpicBox")
    public R adpicBox(){
        List<AdpicEntity> list = adpicService.adpicBox();
        return R.ok(list);
    }

}
