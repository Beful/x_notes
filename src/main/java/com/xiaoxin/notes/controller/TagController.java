package com.xiaoxin.notes.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.TagEntity;
import com.xiaoxin.notes.service.TagService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 标签表
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 10:58:34
 */
@Api(tags = "TagController", description = "标签管理")
@RestController
@RequestMapping("notes/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        params.put(Constant.LIMIT,params.get("pageSize"));
        params.put(Constant.PAGE,params.get("pagenum"));
        PageUtils page = tagService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		TagEntity tag = tagService.getById(id);
        return R.ok(tag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TagEntity tag){
        tag.setCreateTime(new Date());
		tagService.save(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody TagEntity tag){
		tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Integer id){
		tagService.removeById(id);
        return R.ok();
    }

}
