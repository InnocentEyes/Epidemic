package com.qzlnode.epidemic.miniprogram.util;

import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzlzzz
 */
public class ReturnValueHandler {

    public static List handlerReturnValue(String[] values,Class object){
        if(object.isAssignableFrom(Province.class)){
            return handlerPRValue(values);
        }
        if(object.isAssignableFrom(Comment.class)){
            return handlerCRvalue(values);
        }
        return null;
    }

    private static List<String> handlerCRvalue(String[] values){
        int length = values.length;
        List<String> list = new ArrayList<>();
        for(int i = 0 ; i < length ; i++){
            String[] tmp = values[i].split("/");
            list.add(   "{" +
                            "\"userId\":" + tmp[0] +
                            ", \"userName\":\"" + tmp[1] + "\"" +
                            ", \"time\":\"" + tmp[2] + "\"" +
                            ", \"comment\":\"" + tmp[3] + "\"" +
                            '}'
                    );
        }
        Assert.notEmpty(list,"list is null");
        return list;
    }

    private static List<Province> handlerPRValue(String[] values){
        int length = values.length;
        List<Province> allData = new ArrayList<>();
        for(int i = 0 ; i < length ; i++){
            Province province = JsonUtil.jsonToProvince(values[i]);
            if(province != null) allData.add(province);
        }
        return allData;
    }

}
