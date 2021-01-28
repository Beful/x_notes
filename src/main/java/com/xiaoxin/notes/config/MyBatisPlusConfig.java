package com.xiaoxin.notes.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created on 2021/1/10.
 *
 * @author XiaoXinZai
 */
//Spring boot方式
@EnableTransactionManagement
@Configuration
@MapperScan("com.xiaoxin.notes.mapper")
public class MyBatisPlusConfig {
    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制
        //paginationInterceptor.setLimit(2);
        return paginationInterceptor;
    }
}
