package com.qzlnode.epidemic.miniprogram.util;

import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.springframework.util.Assert;

import javax.servlet.http.HttpSession;

/**
 * @author qzlzzz
 */
public class MessageHolder {

    //session id,线程本地变量
    private static final ThreadLocal<String> sidLocal = new ThreadLocal<>();
    //用户信息,线程本地变量
    private static final ThreadLocal<User> userLocal = new ThreadLocal<>();



    /**
     * 取得 user
     * @return
     */
    public static User getUser(){
        User user = userLocal.get();
        Assert.notNull(user,"user未设置");
        return  user;
    }

    /**
     *
     * @param user
     */
    public static void setUser(User user){
        userLocal.set(user);
    }


    /**
     * 清楚线程局部变量,防止内存泄漏
     */
    public static void clearData(){
        userLocal.remove();
        sidLocal.remove();
    }

    /**
     * 获取session中的 userId
     * @return
     */
    public static Integer getUserId(){
        User user = getUser();
        if(user.getId() == 0){
            throw new IllegalArgumentException("user id is null");
        }
        return user.getId();
    }

    /**
     *
     * @return
     */
    public static String getSidId(){
        return sidLocal.get();
    }

    /**
     *
     * @param sidId
     */
    public static void setSidId(String sidId){
        sidLocal.set(sidId);
    }
}
