package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.ArticleLike;
import com.xiaoxin.notes.entity.document.ChatReadHistory;

import java.util.List;

/**
 * Created on 2021/1/27.
 *
 * @author XiaoXinZai
 */
public interface ChatRoomService {

    int create(ChatReadHistory chatReadHistory);

    List<ChatReadHistory> list();

}
