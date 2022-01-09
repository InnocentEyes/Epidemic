package com.qzlnode.epidemic.miniprogram.service.Impl;

import com.qzlnode.epidemic.miniprogram.dao.mysql.UserDao;
import com.qzlnode.epidemic.miniprogram.dao.redis.Impl.RedisForUser;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.IndexService;
import com.qzlnode.epidemic.miniprogram.util.BASE64;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import io.lettuce.core.RedisConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.sql.SQLSyntaxErrorException;

/**
 * @author qzlzzz
 */
@Service
public class IndexServiceImpl implements IndexService {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Integer MAX_PASSWORD_LONG = 30;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisForUser redisForUser;

    /**
     *
     * @param user
     * @return
     */
    @Transactional(
            rollbackFor = {
                RedisConnectionException.class,
                    SQLSyntaxErrorException.class
            }
    )
    @Override
    public User loginService(User user) {
        Assert.notNull(user,"user in login service is null");
        if(user.getId() != null) return user;
        String password = BASE64.encode(user.getUserPassword());
        user.setUserPassword(password);
        User tmp = userDao.findUser(user);
        if(tmp != null){
            user.setId(tmp.getId());
            user.setUserName(tmp.getUserName());
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
    @Transactional(
            rollbackFor = {
                    SQLSyntaxErrorException.class,
                    RedisConnectionException.class
            }
    )
    @Override
    public boolean registerService(User user) {
        Assert.notNull(user,"register service : user is null");
        String password = BASE64.encode(user.getUserPassword());
        if(password.length() > MAX_PASSWORD_LONG){
            logger.info("user phoneNumber: {} password too longer",user.getUserPhoneNumber());
            return false;
        }
        user.setUserPassword(password);
        String[] userMessage = redisForUser.get(user);
        if(userMessage != null){
            //已经登录
            return false;
        }
        boolean target = userDao.registerUser(user);
        if(target){
            logger.info("user phoned : "+user.getUserPhoneNumber()+" register");
        }
        return target;
    }
}
