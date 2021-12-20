package com.qzlnode.epidemic.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.dao.mysql.MainDao;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.util.In;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author qzlzzz
 */
@Component
public class ChangeData {

    //日志
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper mapper = new ObjectMapper();

    //文件位置
    private static final String FILE_PATH = "C:\\Users\\邱泽林\\IdeaProjects\\Epidemic\\last_day_corona_virus_of_china.json";

    private static final String CHARSET = "GBK";

    @Autowired
    private MainDao mainDao;


    @Scheduled(fixedRate = 1000 * 60 * 60 * 23)
    @Test
    public void dataChange(){
        try {
            Process exec = Runtime.getRuntime().exec("python ./corona_virus_spider.py");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream(),CHARSET));
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }
            bufferedReader.close();
            if(exec.exitValue() == 0) {
                In in = new In(FILE_PATH);
                Province[] provinces = mapper.readValue(in.readAll(), Province[].class);
                for (Province province : provinces) {
                    System.out.println(province);
                }
            }

//            List<String> list = new ArrayList<>();
//            for (Province province : provinces) {
//                list.add(JsonUtil.ProvinceToJson(province));
//            }
//            if(list.size() <= 0){
//                throw new NullPointerException("list is null");
//            }
//            mainDao.changeAll(list);
//            logger.info("change data successful");
        } catch (IOException e) {
            logger.info("change data error, \n {}",e.getStackTrace());
//        }catch (InterruptedException e){
//            logger.info("thread woke up, \n {}",e.getStackTrace());
        }
    }
}
