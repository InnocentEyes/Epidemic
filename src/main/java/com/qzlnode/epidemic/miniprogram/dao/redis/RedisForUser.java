package com.qzlnode.epidemic.miniprogram.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisForUser {

    /**
     * redis的客户端工具
     */
    @Autowired
    private StringRedisTemplate redisTemplate;



}
