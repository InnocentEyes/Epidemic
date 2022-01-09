package com.qzlnode.epidemic.miniprogram.service.Impl;

import com.qzlnode.epidemic.miniprogram.dao.mysql.MainDao;
import com.qzlnode.epidemic.miniprogram.dao.redis.Impl.RedisForMain;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.service.MainService;
import com.qzlnode.epidemic.miniprogram.util.JsonUtil;
import com.qzlnode.epidemic.miniprogram.util.ReturnValueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author qzlzzz
 */
@Service
public class MainServiceImpl implements MainService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisForMain redis;

    @Autowired
    private MainDao mainDao;

    /**
     *
     * @return
     */
    @Override
    public List<Province> getAllData() {
        String[] data = redis.get(null);
        if(data != null){
            logger.info("get the epidemic data on redis at {}",new Date());
            return ReturnValueHandler.handlerReturnValue(data, Province.class);
        }
        List<String> allData = mainDao.findAll();
        data = allData.toArray(new String[allData.size()]);
        for (String datum : data) {
            Province province = JsonUtil.jsonToProvince(datum);
            if(!redis.set(province)){
                throw new RuntimeException("redis data set error");
            }
        }
        logger.info("get the epidemic data on mysql and put the epidemic to redis at {}",new Date());
        return ReturnValueHandler.handlerReturnValue(data,Province.class);
    }
}
