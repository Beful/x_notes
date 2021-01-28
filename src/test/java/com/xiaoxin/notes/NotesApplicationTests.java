package com.xiaoxin.notes;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.xiaoxin.notes.entity.document.ChatReadHistory;
import com.xiaoxin.notes.service.ChatRoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class NotesApplicationTests {

    @Autowired
    private ChatRoomService chatRoomService;

    @Test
    void contextLoads() {
        ChatReadHistory chatReadHistory = new ChatReadHistory();
        chatReadHistory.setName("zz");
        chatReadHistory.setContent("没人退了哈！！！");
        int i = chatRoomService.create(chatReadHistory);
        System.out.println(i);
        List<ChatReadHistory> list = chatRoomService.list();
        System.out.println(list.toString());
    }


}
