package com.qzlnode.epidemic.miniprogram.dao.redis.Impl;

import com.qzlnode.epidemic.miniprogram.dao.redis.CommonRedis;
import com.qzlnode.epidemic.miniprogram.dao.redis.Operations;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author qzlzzz
 */
@Component
public class RedisForComment implements CommonRedis<Comment>,Operations<ZSetOperations<String, String>>{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String,ZSetOperations<String, String>> map = new HashMap<>();

    private AtomicInteger atomic = new AtomicInteger(0);

    private Queue<Thread> queue = new LinkedList<>();

    @Autowired
    private StringRedisTemplate redis;

    /**
     *
     * @return
     */
    @Override
    public String[] get(Comment comment) {
        ZSetOperations<String, String> operation = getOperation();
        String key = "comment_type_no :"+comment.getTypeNo();
        Set<String> typeComments = operation.range(key, 0, -1);
        String[] res = typeComments.toArray(new String[typeComments.size()]);
        if(res.length == 0){
            return null;
        }
        return res;
    }

    /**
     *
     */
    @Override
    public void set(Comment comment) {
        User user = MessageHolder.getUser();
        String key = "comment_type_no :"+comment.getTypeNo();
        Date dayNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String value = user.getId() + "/" + user.getUserName() + "/" + ft.format(dayNow) + "/" +comment.getComment();
        ZSetOperations<String, String> operation = getOperation();
        try {
            operation.add(key,value,comment.getLikes());
            redis.expireAt(key,new Date(System.currentTimeMillis() + 1000*60*60*24));
            logger.info("user send message to redis acc");
        }catch (Exception e){
            logger.info("user send message to redis error");
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public ZSetOperations<String, String> getOperation() {
        while (!atomic.compareAndSet(0,1)){
            queue.add(Thread.currentThread());
            LockSupport.park();
        }
        if(map.get("operation") == null){
            map.put("operation",redis.opsForZSet());
        }
        atomic.compareAndSet(1,0);
        LockSupport.unpark(queue.poll());
        return map.get("operation");
    }
}
