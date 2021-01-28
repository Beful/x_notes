package com.xiaoxin.notes.controller;

import java.util.Arrays;
import java.util.Map;

import com.xiaoxin.notes.entity.AriticleTagRelationEntity;
import com.xiaoxin.notes.service.AriticleTagRelationService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 文章-标签关联表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
@RestController
@RequestMapping("notes/ariticletagrelation")
public class AriticleTagRelationController {
    @Autowired
    private AriticleTagRelationService ariticleTagRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ariticleTagRelationService.queryPage(params);

        return R.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		AriticleTagRelationEntity ariticleTagRelation = ariticleTagRelationService.getById(id);

        return R.ok(ariticleTagRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AriticleTagRelationEntity ariticleTagRelation){
		ariticleTagRelationService.save(ariticleTagRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AriticleTagRelationEntity ariticleTagRelation){
		ariticleTagRelationService.updateById(ariticleTagRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		ariticleTagRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
