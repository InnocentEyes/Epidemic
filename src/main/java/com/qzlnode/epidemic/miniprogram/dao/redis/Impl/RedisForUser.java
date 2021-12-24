package com.qzlnode.epidemic.miniprogram.dao.redis.Impl;

import com.qzlnode.epidemic.miniprogram.dao.redis.CommonRedis;
import com.qzlnode.epidemic.miniprogram.dao.redis.Operations;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qzlzzz
 */
@Component
public class RedisForUser implements CommonRedis<User>, Operations<HashOperations<String, Object, Object>> {

    private Map<String,HashOperations<String, Object, Object>> map = new HashMap<>();

    /**
     * 独占锁,乐观锁。可设置为公平锁,但是会影响效率。
     */
    private final ReentrantLock lock = new ReentrantLock();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final long TRY_CATCH_TIME = 500;

    private static final String OPERATION = "operation";

    @Autowired
    private StringRedisTemplate redis;

    /**
     *
     * @return
     */
    @Override
    public String[] get(User user){
        HashOperations<String, Object, Object> operation = getOperation();
        String key = "user_phone :"+user.getUserPhoneNumber();
        String userId = "";
        String userPassword = "";
        try {
            userId = (String)operation.get(key, "id");
            userPassword = (String) operation.get(key,"password");
        }catch (Exception e){
            logger.error("get the user{} id error",new Object[]{user.getId()});
        }
        if(userId == null){
            return null;
        }
        return new String[]{userId,userPassword};
    }

    /**
     * <h3></h3>
     */
    @Override
    public void set(User user) {
        if(user.getId() == 0){
            logger.info("user named :"+user.getUserName()+"who user id is null");
            throw new IllegalArgumentException("user id is null");
        }
        HashOperations<String, Object, Object> operation = getOperation();
        Map<String,Object> userMessage = new HashMap<>();
        String key = "user_phone :"+user.getUserPhoneNumber();
        userMessage.put("id",user.getId().toString());
        userMessage.put("password",user.getUserPassword());
        try {
            operation.putAll(key, userMessage);
            redis.expireAt(key,new Date(System.currentTimeMillis() + 1000*60*30));
        }catch (Exception e){
            logger.error("put the user named "+user.getUserName()+" message to redis error \n {}",e.getMessage());
            return;
        }
        logger.info("put the user named"+user.getUserName()+"message to redis acc");
    }



    /**
     *
     * @return
     */
    @Override
    public HashOperations<String, Object, Object> getOperation(){
        try {
            if(lock.tryLock(TRY_CATCH_TIME, TimeUnit.MILLISECONDS)){
                try{
                    if(map.get(OPERATION) == null){
                        map.put(OPERATION,redis.opsForHash());
                    }
                }finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            logger.info(Thread.currentThread().getName()+"was interrupted");
        }
        return map.get(OPERATION);
    }
}
