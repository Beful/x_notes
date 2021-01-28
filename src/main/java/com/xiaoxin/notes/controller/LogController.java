package com.xiaoxin.notes.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.LogEntity;
import com.xiaoxin.notes.service.LogService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @date 2021-01-12 20:18:07
 */
@Api(tags = "LogController", description = "日志")
@RestController
@RequestMapping("notes/log")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam int pagenum,@RequestParam int pageSize){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constant.PAGE,pagenum);
        params.put(Constant.LIMIT,pageSize);
        PageUtils page = logService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		LogEntity log = logService.getById(id);
        return R.ok(log);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody LogEntity log){
		logService.save(log);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody LogEntity log){
		logService.updateById(log);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		logService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
