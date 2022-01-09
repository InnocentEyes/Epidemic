package com.qzlnode.epidemic.miniprogram.util;

import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                            "\"commentId\":" + tmp[0] +
                            "\"userId\":" + tmp[1] +
                            ", \"userName\":\"" + tmp[2] + "\"" +
                            ", \"time\":\"" + tmp[3] + "\"" +
                            ", \"comment\":\"" + tmp[4] + "\"" +
                            '}'
                    );
        }
        Assert.notEmpty(list,"list is null");
        return list.stream().map(element -> {
            return element.replace("\\","");
        }).collect(Collectors.toList());
    }

    private static List<Province> handlerPRValue(String[] values){
        List<Province> allData = new ArrayList<>();
        for (String value : values) {
            allData.add(JsonUtil.jsonToProvince(value));
        }
        return allData;
    }

}
