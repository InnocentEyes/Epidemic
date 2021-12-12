package com.qzlnode.epidemic.miniprogram.service;


import com.mysql.cj.xdevapi.JsonArray;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.List;

/**
 *
 */
public interface MainService {

    /**
     *
     * @return
     */
    List<Province> getAllData();
}
