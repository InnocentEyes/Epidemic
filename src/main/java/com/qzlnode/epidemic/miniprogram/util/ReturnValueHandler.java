package com.qzlnode.epidemic.miniprogram.util;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzlzzz
 */
public class ReturnValueHandler {

    public static List<String> handlerCRvalue(String[] values){
        int length = values.length;
        List<String> list = new ArrayList<>();
        for(int i = 0 ; i < length ; i++){
            String[] tmp = values[i].split("/");
            list.add(i + "{" +
                            "userId=" + tmp[0] +
                            ", userName='" + tmp[1] + "\'" +
                            ", time='" + tmp[2] + "\'" +
                            ", comment=" + tmp[3] +
                            '}'
                    );
        }
        Assert.notEmpty(list,"list is null");
        return list;
    }
}
