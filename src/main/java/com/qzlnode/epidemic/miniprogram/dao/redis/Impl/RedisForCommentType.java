package com.qzlnode.epidemic.miniprogram.dao.redis.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.qzlnode.epidemic.miniprogram.dao.redis.CommonRedis;
import com.qzlnode.epidemic.miniprogram.dao.redis.Operations;
import com.qzlnode.epidemic.miniprogram.dto.CommentView;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author qzlzzz
 */
@Component
public class RedisForCommentType implements CommonRedis<Comment>,Operations<ZSetOperations<String, String>>{

    private static final String OPERATION = "operation";

    private static final String KEY_NAME = "commentType_No:";

    private static final long EFFECTIVE_TIME = 1000 * 60 * 60 * 24;

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        String key = KEY_NAME + comment.getTypeNo();
        Set<String> typeComments = operation.rangeByScore(key, 0, -1);
        String[] res = typeComments.stream().toArray(String[]::new);
        if(res.length == 0){
            return null;
        }
        return res;
    }

    /**
     *
     */
    @Override
    public boolean set(Comment comment) {
        String key = KEY_NAME + comment.getTypeNo();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setConfig(mapper.getSerializationConfig().withView(CommentView.Detail.class));
        try {
            String value = mapper.writeValueAsString(comment);
            ZSetOperations<String, String> operation = getOperation();
            operation.add(key,value.substring(0,value.indexOf("likes") - 2) + "}",comment.getLikes());
            redis.expireAt(key,new Date(System.currentTimeMillis() + EFFECTIVE_TIME));
            return true;
        } catch (JsonProcessingException | RedisConnectionException | RedisCommandTimeoutException e) {
            logger.error("handler json or redis command error !");
            return false;
        } catch (Exception e){
            logger.error("execute the method is error. the reason is {}",e.getMessage());
        }
        return false;
    }

    /**
     *
     * @param comment
     * @return
     */
    @Override
    public boolean update(Comment comment) {
        if(comment.getComment() != null){
            return updateComment(comment);
        }
        return updateLikes(comment);
    }

    /**
     * 此功能未开发
     * @param comment
     * @return
     */
    private boolean updateComment(Comment comment){
        String key = KEY_NAME + comment.getTypeNo();
        return false;
    }

    /**
     *
     * @param comment
     * @return
     */
    private boolean updateLikes(Comment comment){
        String key = KEY_NAME + comment.getTypeNo();
        ObjectMapper mapper = new ObjectMapper();
        ZSetOperations<String, String> operation = getOperation();
        Set<String> range = operation.range(key, 0, -1);
        List<String> list = range.stream()
                .filter(element -> element.indexOf(comment.getCommentId()) != -1)
                .collect(Collectors.toList());
        if(list.size() > 1) return false;
        operation.incrementScore(key,list.get(0),1);
        return true;
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
        if(map.get(OPERATION) == null){
            map.put(OPERATION,redis.opsForZSet());
        }
        atomic.compareAndSet(1,0);
        LockSupport.unpark(queue.poll());
        return map.get(OPERATION);
    }
}
