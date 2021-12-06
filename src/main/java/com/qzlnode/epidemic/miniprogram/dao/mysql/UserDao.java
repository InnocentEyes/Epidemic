package com.qzlnode.epidemic.miniprogram.dao.mysql;

import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User findUser(User user);

}
