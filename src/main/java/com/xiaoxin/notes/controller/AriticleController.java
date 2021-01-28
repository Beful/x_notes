package com.xiaoxin.notes.controller;
import java.util.Date;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.AriticleEntity;
import com.xiaoxin.notes.entity.vo.AriticleVo;
import com.xiaoxin.notes.service.AriticleService;
import com.xiaoxin.notes.service.RedisService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 文章表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
@RestController
@RequestMapping("notes/ariticle")
public class AriticleController {
    @Autowired
    private AriticleService ariticleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        params.put(Constant.LIMIT,params.get("pageSize"));
        params.put(Constant.PAGE,params.get("pagenum"));
        PageUtils page = ariticleService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 信息 留言
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        AriticleEntity ariticle = ariticleService.getAriticleCoById(id);
        return R.ok(ariticle);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AriticleVo ari){
        ariticleService.saveAriticle(ari);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AriticleVo ari){
		ariticleService.updateAriticle(ari);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		ariticleService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    @GetMapping("/sortTagList")
    public R sortTagList(){
        Map<String, Object> map = ariticleService.sortTagList();
        return R.ok(map);
    }





}
