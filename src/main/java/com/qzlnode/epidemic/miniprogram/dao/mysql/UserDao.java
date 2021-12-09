package com.qzlnode.epidemic.miniprogram.dao.mysql;

import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <h2>关于user表的查询接口</h2>
 * @author qzlzzz
 * @version 1.0
 */
@Mapper
public interface UserDao {

    /**
     * <h3>根据User提供的信息来查找</h3>
     * @param user user message
     * @return {@code true} or {@code false}
     */
    Integer findUser(User user);

    /**
     * <h3></h3>
     * @param user
     * @return
     */
    boolean registerUser(User user);
}
