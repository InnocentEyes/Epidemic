package com.qzlnode.epidemic.miniprogram.pointcut;

import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author qzlzzz
 */
@Component
@Aspect
public class HandlerException {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());



    @AfterThrowing(throwing = "serviceEx",pointcut = "within(com.qzlnode.epidemic.miniprogram.service..*)")
    public void handlerServiceError(Exception serviceEx){
        MessageHolder.clearData();
        logger.error("handler method error :"+serviceEx.getCause()+"\n" + "the detail is : " + serviceEx.getMessage());
    }

//    @AfterThrowing(throwing = "daoEx",pointcut = "execution(public * com.qzlnode.epidemic.miniprogram.dao.mysql.*.*(..))")
//    public void handlerDaoError(Exception daoEx){
//        MessageHolder.clearData();
//        logger.error("handler method error :"+daoEx.getCause()+"\n" + "the detail is : " + daoEx.getMessage());
//    }

    @AfterThrowing(throwing = "redisEx",pointcut = "this(com.qzlnode.epidemic.miniprogram.dao.redis.CommonRedis)")
    public void handlerRedisError(Exception redisEx){
        MessageHolder.clearData();
        logger.error("handler method error :"+redisEx.getCause()+"\n" + "the detail is : "+redisEx.getMessage());
    }
}
