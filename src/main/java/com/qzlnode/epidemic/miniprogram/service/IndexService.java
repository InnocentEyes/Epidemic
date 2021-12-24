package com.qzlnode.epidemic.miniprogram.service;

import com.qzlnode.epidemic.miniprogram.pojo.User;

/**
 * 登录页的业务接口
 * @since 2021/12/06
 * @author qzlzzz
 */
public interface IndexService {

    /**
     * 登录服务
     * @param user
     * @return
     */
    User loginService(User user);

    /**
     * 注册服务
     * @param user
     * @return
     */
    boolean registerService(User user);
}
