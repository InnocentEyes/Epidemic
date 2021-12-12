package com.qzlnode.epidemic.miniprogram.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.pojo.Province;

/**
 *
 */
public class JsonUtil {

    /**
     *
     * @param json
     * @return
     */
    public static Province JsonToProvince(String json){
        ObjectMapper mapper = new ObjectMapper();
        Province province = null;
        try {
            province = mapper.readValue(json, Province.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return province;
    }

    /**
     *
     * @param province
     * @return
     */
    public static String ProvinceToJson(Province province){
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(province);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
