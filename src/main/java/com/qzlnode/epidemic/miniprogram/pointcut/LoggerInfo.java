package com.qzlnode.epidemic.miniprogram.pointcut;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author qzlzzz
 */
@Component
@Aspect
public class LoggerInfo {

    /**
     *
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(value = "execution(* com.qzlnode.epidemic.miniprogram.controller.*Controller.*(..)) " +
            "&& !@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void controlPointCut(){

    }

    @Before(value = "bean(*Impl) && args(object)")
    public void serviceMethodArgsInfo(JoinPoint joinPoint,Object object){
        logger.info("handler this method in {},the handler method is {},and the method args is {}",
                new Date(),
                joinPoint.getSignature(),
                object);
    }

//    @AfterReturning(value = "bean(*Impl) && args(com.qzlnode.epidemic.miniprogram.pojo.*)",returning = "object")
//    public Object serviceMethodReturnInfo(JoinPoint joinPoint, Object object){
//        logger.info("handler method is {},the method return value is {}. finish at {}",
//                joinPoint.toShortString(),
//                object,
//                new Date());
//        return object;
//    }

    @Before(value = "execution(* com.qzlnode.epidemic.miniprogram.controller.*Controller.*(..)) " +
            "&& !@annotation(org.springframework.web.bind.annotation.ExceptionHandler)" +
            "&& args(object)")
    public void controlMethodInfo(JoinPoint joinPoint,Object object){
        logger.info("handler this method {} at {} , the method args is {}",
                joinPoint.toShortString(),
                new Date(),
                object);
    }

//    @AfterReturning(value = "execution(* com.qzlnode.epidemic.miniprogram.controller.*Controller.*(..)) " +
//            "&& !@annotation(org.springframework.web.bind.annotation.ExceptionHandler)" +
//            "&& args(java.lang.Object)",returning = "object")
//    public Object controlMethodReturnInfo(JoinPoint joinPoint,Object object){
//        logger.info("the method {} handler at {} , the method return value is {}",
//                joinPoint.toShortString(),
//                new Date(),
//                object);
//        return object;
//    }


}
