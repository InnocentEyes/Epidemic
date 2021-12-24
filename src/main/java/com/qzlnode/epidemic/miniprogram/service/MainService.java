package com.qzlnode.epidemic.miniprogram.service;


import com.mysql.cj.xdevapi.JsonArray;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.List;

/**
 * @author qzlzzz
 */
public interface MainService {

    /**
     * 获取全省市区的疫情数据
     * @return
     */
    List<Province> getAllData();
}
