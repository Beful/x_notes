package com.xiaoxin.notes.schedule;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.config.RedisConfig;
import com.xiaoxin.notes.entity.MenuEntity;
import com.xiaoxin.notes.service.MenuService;
import com.xiaoxin.notes.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created on 2021/1/17.
 *
 * @author XiaoXinZai
 */
@Component
@Slf4j
public class RedisMenuSchedule {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MenuService menuService;

    /**
     * 更新菜单到 Redis缓存数据库
     *
     * 直接指定时间间隔，例如：24小时
     */
    @Scheduled(fixedRate=1000 * 60 * 60 * 24)
    public void updateRedisMenu() {
        redisTemplate.delete(RedisConfig.LIST_KEY_MENULIST);
        List<MenuEntity> list = menuService.menuList();
        for (MenuEntity menu : list) {
            redisTemplate.opsForList().rightPush(RedisConfig.LIST_KEY_MENULIST, menu);
        }
    }

}
