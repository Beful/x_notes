package com.xiaoxin.notes.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.AdpicEntity;
import com.xiaoxin.notes.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2021-01-13 14:01:44
 */
public interface AdpicService extends IService<AdpicEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AdpicEntity> imageBox();

    List<AdpicEntity> adpicBox();
}

