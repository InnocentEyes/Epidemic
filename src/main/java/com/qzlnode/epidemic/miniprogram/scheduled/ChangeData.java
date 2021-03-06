package com.qzlnode.epidemic.miniprogram.scheduled;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.dao.mysql.MainDao;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.util.In;
import com.qzlnode.epidemic.miniprogram.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qzlzzz
 */
@Component
public class ChangeData {

    /**
     *
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     */
    private static final String CHARSET_NAME = "GBK";

    /**
     * 更新数据库的文件的位置,默认在项目下面
     */
    private static final String FILE_PATH;

    static {
        URL url = ChangeData.class.getResource("");
        assert url != null;
        String path = url.getPath();
        try {
            path = URLDecoder.decode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        FILE_PATH = path.substring(1,path.indexOf("Epidemic") + "Epidemic".length()) + "/last_day_corona_virus_of_china.json";
    }


    @Autowired
    private MainDao mainDao;


    /**
     *
     */
    @Transactional(rollbackFor = {
            IOException.class
    })
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void dataChange(){
        try {
            Process exec = Runtime.getRuntime().exec("python ./corona_virus_spider.py");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream(),CHARSET_NAME));
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }
            bufferedReader.close();

            if(exec.exitValue() != 0) {
                logger.error("run the py file error !");
                return;
            }
            In in = new In(FILE_PATH);
            Province[] provinces = mapper.readValue(in.readAll(), Province[].class);
            List<String> list = new ArrayList<>();
            for (Province province : provinces) {
                list.add(JsonUtil.provinceToJson(province));
            }
            if (list.size() <= 0) {
                logger.error("the file path position is error");
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
