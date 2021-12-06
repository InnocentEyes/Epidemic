package com.qzlnode.epidemic.miniprogram.util;

import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.springframework.util.Assert;

import javax.servlet.http.HttpSession;

/**
 * @author qzlzzz
 */
public class SessionHolder {

    //session id,线程本地变量
    private static final ThreadLocal<String> sidLocal = new ThreadLocal<>();
    //session,线程本地变量
    private static final ThreadLocal<HttpSession> sessionLocal = new ThreadLocal<>();
    //用户信息,线程本地变量
    private static final ThreadLocal<User> sessionUserLocal = new ThreadLocal<>();

    /**
     * 保存session在线程本地变量中
     * @param session
     */
    public static void setSession(HttpSession session){
        sessionLocal.set(session);
    }

    /**
     * 取得绑定在线程本地变量中的session
     * @return
     */
    public static HttpSession getSession(){
        HttpSession httpSession = sessionLocal.get();
        Assert.notNull(httpSession,"session未设置");
        return httpSession;
    }

    /**
     * 取得session user
     * @return
     */
    public static User getSessionUser(){
        User user = sessionUserLocal.get();
        Assert.notNull(user,"session未设置");
        return  user;
    }

    /**
     * 清楚线程局部变量,防止内存泄漏
     */
    public static void clearData(){
        sessionLocal.remove();
        sessionUserLocal.remove();
        sidLocal.remove();
    }

    /**
     * 获取session中的 userId
     * @return
     */
    public static Integer getUserId(){
        User user = getSessionUser();
        Assert.notNull(user,"session user is null");
        return user.getId();
    }

    public static String getSidId(){
        return sidLocal.get();
    }

    public static void setSidId(String sidId){
        sidLocal.set(sidId);
    }
}
