package com.xiaoxin.notes.controller;

import java.util.Arrays;
import java.util.Map;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.entity.QuartzEntity;
import com.xiaoxin.notes.service.QuartzService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 定时任务
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-18 20:58:32
 */
@RestController
@RequestMapping("notes/quartz")
public class QuartzController {
    @Autowired
    private QuartzService quartzService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        params.put(Constant.LIMIT,params.get("pageSize"));
        params.put(Constant.PAGE,params.get("pagenum"));
        PageUtils page = quartzService.queryPage(params);

        return R.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		QuartzEntity quartz = quartzService.getById(id);

        return R.ok(quartz);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody QuartzEntity quartz){
		quartzService.save(quartz);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody QuartzEntity quartz){
		quartzService.updateById(quartz);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		quartzService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
