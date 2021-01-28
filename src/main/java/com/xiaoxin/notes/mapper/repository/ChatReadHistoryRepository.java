package com.xiaoxin.notes.mapper.repository;

import com.xiaoxin.notes.entity.document.ChatReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created on 2021/1/27.
 *
 * @author XiaoXinZai
 */
public interface ChatReadHistoryRepository extends MongoRepository<ChatReadHistory,String> {
    /**
     * 查询当天聊天信息
     * @param dateId
     * @return
     */
    List<ChatReadHistory> findByDateIdOrderByCreateTime(int dateId);
}
