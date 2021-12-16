package com.qzlnode.epidemic.miniprogram.dao.redis.Impl;

import com.qzlnode.epidemic.miniprogram.dao.redis.CommonRedis;
import com.qzlnode.epidemic.miniprogram.dao.redis.Operations;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.util.JsonUtil;
import com.qzlnode.epidemic.miniprogram.util.ParseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qzlzzz
 */
@Component
public class RedisForMain implements CommonRedis<Province>, Operations<ListOperations<String, String>> {

    private final ReentrantLock lock = new ReentrantLock();

    private Map<String,ListOperations<String, String>> map = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String KEY = "all";

    @Autowired
    private StringRedisTemplate redis;

    /**
     *
     * @param object
     * @return
     */
    @Override
    public String[] get(Province object) {
        ListOperations<String, String> operation = getOperation();
        List<String> data = operation.range(KEY, 0, -1);
        if(data.size() == 0){
            return null;
        }
        return data.toArray(new String[data.size()]);
    }

    /**
     *
     * @param object
     */
    @Override
    public void set(Province object) {
        String json = JsonUtil.ProvinceToJson(object);
        ListOperations<String, String> operation = getOperation();
        try {
            operation.rightPush(KEY,json);
            redis.expireAt(KEY,new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        }catch (Exception e){
            logger.info("send data to redis error");
        }
    }

    /**
     *
     * @return
     */
    @Override
    public ListOperations<String, String> getOperation() {
        lock.lock();
        try {
            if (map.get("operation") == null) {
                map.put("operation", redis.opsForList());
            }
        }finally {
            lock.unlock();
        }
        return map.get("operation");
    }
}
