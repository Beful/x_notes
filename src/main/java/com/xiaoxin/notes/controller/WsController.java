package com.xiaoxin.notes.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xiaoxin.notes.config.RedisConfig;
import com.xiaoxin.notes.controller.ex.RunServerException;
import com.xiaoxin.notes.entity.document.ChatReadHistory;
import com.xiaoxin.notes.service.ChatRoomService;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2021/1/26.
 *
 *
 * @author XiaoXinZai
 */
@Api(tags = "WsController", description = "聊天室系统管理")
@RestController
@RequestMapping("/chatRoom")
public class WsController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private RedisTemplate redisTemplate;

    @MessageMapping("/hello")
    public R send(String message, @Header("Authorization") String topic,
                       @Headers Map<String, Object> headers) {
        Gson gson = new Gson();
        Map map = gson.fromJson(message, Map.class);
        String userId = map.get("userId").toString();
        List<String> list = redisTemplate.opsForList().range(RedisConfig.MAP_KEY_CHAT_USER, 0,-1);
        if(list == null){
            redisTemplate.opsForList().rightPush(RedisConfig.MAP_KEY_CHAT_USER,userId);
            redisTemplate.opsForValue().increment(RedisConfig.STRING_KEY_CHAT_USER_COUNT,1);
        } else {
            long count = list.stream().filter(user -> user.equals(userId)).count();
            if (count == 0) {
                redisTemplate.opsForList().rightPush(RedisConfig.MAP_KEY_CHAT_USER,userId);
                redisTemplate.opsForValue().increment(RedisConfig.STRING_KEY_CHAT_USER_COUNT, 1);
                simpMessagingTemplate.convertAndSend("/server/sendMessageByServer", "新加入用户");
            }
        }
        return R.ok();
    }

    @ApiOperation("新增聊天记录")
    @PostMapping("create")
    public R create(@RequestBody ChatReadHistory chatReadHistory) {
        int i = chatRoomService.create(chatReadHistory);
        if(i != 1){
            throw new RunServerException("出现错误！");
        }
        simpMessagingTemplate.convertAndSend("/server/sendMessageByServer", chatReadHistory);
        return R.ok();
    }

    @ApiOperation("展示聊天记录")
    @GetMapping(value = "list")
    public R list() {
        List<ChatReadHistory> chatRoom = chatRoomService.list();
        Map<String, Object> map = new HashMap<>();
        map.put("list",chatRoom);
        map.put("userCount",redisTemplate.opsForValue().get(RedisConfig.STRING_KEY_CHAT_USER_COUNT));
        map.put("userIds",redisTemplate.opsForList().range(RedisConfig.MAP_KEY_CHAT_USER,0,-1));
        return R.ok(map);
    }

    @ApiOperation("用戶退出登錄")
    @DeleteMapping("disUserCount/{id}")
    public R disUserCount(@PathVariable("id") String id) {
        List<String> list = redisTemplate.opsForList().range(RedisConfig.MAP_KEY_CHAT_USER, 0,-1);
        if(list != null) {
            long count = list.stream().filter(user -> user.equals(id)).count();
            if (count > 0) {
                redisTemplate.opsForList().remove(RedisConfig.MAP_KEY_CHAT_USER, 1, id);
                redisTemplate.opsForValue().increment(RedisConfig.STRING_KEY_CHAT_USER_COUNT, -1);
                simpMessagingTemplate.convertAndSend("/server/sendMessageByServer", "退出群聊");
            }
        }
        return R.ok();
    }


}
