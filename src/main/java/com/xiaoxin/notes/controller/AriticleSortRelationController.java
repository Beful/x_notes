package com.xiaoxin.notes.controller;

import java.util.Arrays;
import java.util.Map;

import com.xiaoxin.notes.entity.AriticleSortRelationEntity;
import com.xiaoxin.notes.service.AriticleSortRelationService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 文章-分类关联表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 13:18:01
 */
@RestController
@RequestMapping("notes/ariticlesortrelation")
public class AriticleSortRelationController {
    @Autowired
    private AriticleSortRelationService ariticleSortRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ariticleSortRelationService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		AriticleSortRelationEntity ariticleSortRelation = ariticleSortRelationService.getById(id);
        return R.ok(ariticleSortRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AriticleSortRelationEntity ariticleSortRelation){
		ariticleSortRelationService.save(ariticleSortRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AriticleSortRelationEntity ariticleSortRelation){
		ariticleSortRelationService.updateById(ariticleSortRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		ariticleSortRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
