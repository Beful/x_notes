package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.LeaverBoardEntity;
import com.xiaoxin.notes.utils.PageUtils;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author 
 * @email ${email}
 * @date 2021-01-23 16:17:52
 */
public interface LeaverBoardService extends IService<LeaverBoardEntity> {

    Integer selByUserIdCount(Integer userid);

    List<LeaverBoardEntity> selList();
}

