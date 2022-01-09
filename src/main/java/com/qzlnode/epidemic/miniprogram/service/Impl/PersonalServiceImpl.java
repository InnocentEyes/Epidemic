package com.qzlnode.epidemic.miniprogram.service.Impl;

import com.qzlnode.epidemic.miniprogram.dao.mysql.UserDao;
import com.qzlnode.epidemic.miniprogram.dao.redis.Impl.RedisForUser;
import com.qzlnode.epidemic.miniprogram.pojo.CommentType;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.PersonalService;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

/**
 * @author qzlzzz
 */
@Service
public class PersonalServiceImpl implements PersonalService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisForUser redis;

    /**
     * 
     * @param user
     * @return
     */
    @Transactional(rollbackFor = {
            SQLSyntaxErrorException.class
    })
    @Override
    public boolean changeMessage(User user) {
        if(!userDao.updateUser(user) || !redis.set(user)){
            logger.error("update user message error !");
            return false;
        }
        logger.info("update user message successful !");
        return true;
    }

    @Override
    public List<CommentType> getRecord7ByUserId() {
        return null;
    }

}
