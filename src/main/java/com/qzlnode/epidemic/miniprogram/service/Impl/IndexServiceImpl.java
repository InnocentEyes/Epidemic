package com.qzlnode.epidemic.miniprogram.service.Impl;

import com.qzlnode.epidemic.miniprogram.dao.mysql.UserDao;
import com.qzlnode.epidemic.miniprogram.dao.redis.Impl.RedisForUser;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.IndexService;
import com.qzlnode.epidemic.miniprogram.util.BASE64;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author qzlzzz
 */
@Service
public class IndexServiceImpl implements IndexService {

    //日志
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisForUser redisForUser;

    /**
     *
     * @param user
     * @return
     */
    @Transactional()
    @Override
    public User LoginService(User user) {
        Assert.notNull(user,"user in login service is null");
        String password = BASE64.encode(user.getUserPassword());
        user.setUserPassword(password);
        String[] userData = redisForUser.get(user);
        Integer userId = -1;
        if(userData != null) {
            if(!userData[1].equals(password)) return user;
            userId = Integer.parseInt(userData[0]);
            user.setId(userId);
            return user;
        }
        userId = userDao.findUser(user);
        if(userId != null && userId != -1){
            user.setId(userId);
            redisForUser.set(user);
            return user;
        }
        return user;
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public boolean registerService(User user) {
        Assert.notNull(user,"register service : user is null");
        String password = BASE64.encode(user.getUserPassword());
        if(password.length() > 30){
            logger.info("user phoneNumber: {} password too longer",user.getUserPhoneNumber());
            return false;
        }
        user.setUserPassword(password);
        if(StringUtils.hasLength(redisForUser.get(user)[0])){
            return false;//已经登录
        }
        boolean target = userDao.registerUser(user);
        if(target){
            logger.info("user phoned :"+user.getUserPhoneNumber()+"register");
        }
        return target;
    }
}
