package com.qzlnode.epidemic.miniprogram.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.pojo.City;
import com.qzlnode.epidemic.miniprogram.pojo.CommentType;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class JsonUtil {

    /**
     *
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     *
     * @param json
     * @return
     */
    public static Province JsonToProvince(String json){
        Assert.hasLength(json,"json must no null");
        try {
            return MAPPER.readValue(json, Province.class);
        } catch (JsonProcessingException e) {
            logger.error("json to province , the json is wrong format");
            return null;
        }
    }

    /**
     *
     * @param province
     * @return
     */
    public static String ProvinceToJson(Province province){
        try {
            return MAPPER.writeValueAsString(province);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String ProvinceToJson(List<Province> provinces){
        try {
            return MAPPER.writeValueAsString(provinces);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param json
     * @return
     */
    public static CommentType JsonToType(String json){
        Assert.hasLength(json,"json must no null");
        try {
            return MAPPER.readValue(json,CommentType.class);
        } catch (JsonProcessingException e) {
            logger.error("json to type , the json is wrong format");
        }
        return null;
    }

    public static String TypeToJson(CommentType commentType){
        try {
            return MAPPER.writeValueAsString(commentType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String TypeToJson(List<CommentType> commentTypeList){
        try {
            return MAPPER.writeValueAsString(commentTypeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param json
     * @return
     */
    public static City JsonToCity(String json){
        Assert.hasLength(json,"json must no null");
        try {
            return MAPPER.readValue(json,City.class);
        } catch (JsonProcessingException e) {
            logger.error("json to city , the json is wrong format");
        }
        return null;
    }

    /**
     *
     * @param city
     * @return
     */
    public static String CityToJson(City city){
        try {
            return MAPPER.writeValueAsString(city);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String CityToJson(List<City> cities){
        try {
            return MAPPER.writeValueAsString(cities);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
