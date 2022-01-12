package com.qzlnode.epidemic.miniprogram.util;

import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author qzlzzz
 */
public class ReturnValueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnValueHandler.class);

    public static <T> List<T> handlerReturnValue(String[] values,Class<T> object){
        if(object.isAssignableFrom(Province.class)){
            return (List<T>) handlerPRValue(values);
        }
        if(object.isAssignableFrom(Comment.class)){
            return (List<T>) handlerCRvalue(values);
        }
        return null;
    }

    private static List<Comment> handlerCRvalue(String[] values){
        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .map(element -> JsonUtil.jsonToComment(element))
                .collect(Collectors.toList());
    }

    private static List<Province> handlerPRValue(String[] values){
        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .map(element -> JsonUtil.jsonToProvince(element))
                .collect(Collectors.toList());
    }

}
