package com.xiaoxin.notes.service.Impl;


import cn.hutool.core.date.DateUtil;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.entity.document.ChatReadHistory;
import com.xiaoxin.notes.mapper.repository.ChatReadHistoryRepository;
import com.xiaoxin.notes.service.ChatRoomService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created on 2021/1/27.
 *
 * @author XiaoXinZai
 */
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatReadHistoryRepository chatReadHistoryRepository;

    @Override
    public int create(ChatReadHistory chatReadHistory) {
        if(StringUtils.isBlank(chatReadHistory.getContent()) || StringUtils.isBlank(chatReadHistory.getName())){
            throw new RunServerException("请确认内容不可为空！");
        }
        chatReadHistory.setId(null);
        chatReadHistory.setDateId(Integer.parseInt(DateUtil.format(new Date(),"yyyyMMdd")));
        chatReadHistory.setMonthId(Integer.parseInt(DateUtil.format(new Date(),"yyyyMM")));
        chatReadHistory.setCreateTime(new Date());
        chatReadHistoryRepository.save(chatReadHistory);
        return 1;
    }

    @Override
    public List<ChatReadHistory> list() {
        int dateId = Integer.parseInt(DateUtil.format(new Date(), "yyyyMMdd"));
        return chatReadHistoryRepository.findByDateIdOrderByCreateTime(dateId);
    }
}
