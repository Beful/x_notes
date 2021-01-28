package com.xiaoxin.notes.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoxin.notes.entity.LeaverBoardEntity;
import com.xiaoxin.notes.service.LeaverBoardService;

/**
 * 
 *
 * @author 
 * @email ${email}
 * @date 2021-01-23 16:17:52
 */
@RestController
@RequestMapping("notes/leaverboard")
public class LeaverBoardController {
    @Autowired
    private LeaverBoardService leaverBoardService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(){
        List<LeaverBoardEntity> list = leaverBoardService.selList();
        return R.ok(list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		LeaverBoardEntity leaverBoard = leaverBoardService.getById(id);
        return R.ok(leaverBoard);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody LeaverBoardEntity leaverBoard){
        if(StringUtils.isBlank(String.valueOf(leaverBoard.getUserid())) || StringUtils.isBlank(leaverBoard.getUsername()) || StringUtils.isBlank(leaverBoard.getContent())){
            throw new RunServerException("不可以评论，请确认状态！");
        }
        if(leaverBoardService.selByUserIdCount(leaverBoard.getUserid()) >= 3){
            throw new RunServerException("目前处于测试阶段，一个用户仅可以评论三条数据！");
        }
        leaverBoard.setCreateTime(new Date());
		leaverBoardService.save(leaverBoard);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody LeaverBoardEntity leaverBoard){
		leaverBoardService.updateById(leaverBoard);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		leaverBoardService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
