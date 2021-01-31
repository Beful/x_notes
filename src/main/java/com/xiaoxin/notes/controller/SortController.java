package com.xiaoxin.notes.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.SortEntity;
import com.xiaoxin.notes.service.SortService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-19 12:26:22
 */
@Api(tags = "SortController", description = "分类管理")
@RestController
@RequestMapping("notes/sort")
public class SortController {
    @Autowired
    private SortService sortService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        params.put(Constant.LIMIT,params.get("pageSize"));
        params.put(Constant.PAGE,params.get("pagenum"));
        PageUtils page = sortService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		SortEntity sort = sortService.getById(id);
        return R.ok(sort);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SortEntity sort){
        sort.setCreateTime(new Date());
		sortService.save(sort);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SortEntity sort){
		sortService.updateById(sort);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Integer id){
		sortService.removeById(id);
        return R.ok();
    }

}
