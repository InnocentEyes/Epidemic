package com.qzlnode.epidemic.miniprogram.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.dao.mysql.MainDao;
import com.qzlnode.epidemic.miniprogram.dao.redis.Impl.RedisForMain;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.util.In;
import com.qzlnode.epidemic.miniprogram.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qzlzzz
 */
@Component
public class ChangeData {

    //日志
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper mapper = new ObjectMapper();

    //文件位置
    private static final String FILE_PATH = "./last_day_corona_virus_of_china.json";

    @Autowired
    private MainDao mainDao;


    @Scheduled(fixedRate = 1000 * 60 * 60 * 23)
    public void dataChange(){
        try {
            Runtime.getRuntime().exec("python ./corona_virus_spider.py");
            In in = new In(FILE_PATH);
            Province[] provinces = mapper.readValue(in.readAll(), Province[].class);
            List<String> list = new ArrayList<>();
            for (Province province : provinces) {
                list.add(JsonUtil.ProvinceToJson(province));
            }
            if(list.size() <= 0){
                throw new NullPointerException("list is null");
            }
            mainDao.changeAll(list);
            logger.info("change data successful");
        } catch (IOException e) {
            logger.info("change data error");
            e.printStackTrace();
        }
    }
}
