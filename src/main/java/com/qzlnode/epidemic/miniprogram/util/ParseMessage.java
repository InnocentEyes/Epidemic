package com.qzlnode.epidemic.miniprogram.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <h2>解析客户端传入的信息，并封装使用</h2>
 * @author qzlzzz
 */
public class ParseMessage {
    /**
     * 解析User信息
     * @param userMessage 信息
     * @return 返回解析好的User对象
     * @throws JsonProcessingException 解析异常
     */
    public static User parseUser(String userMessage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(userMessage);
        User user = new User();
        String phone = tree.get("phone").asText();
        if(!StringUtils.hasLength(phone)){
            throw new IllegalArgumentException("user phone is null");
        }
        user.setUserPhoneNumber(phone);
        String password = tree.get("password").asText();
        if(!StringUtils.hasLength(password)){
            throw new IllegalArgumentException("user password is null");
        }
        user.setUserPassword(password);
        String userName = tree.get("name").asText();
        if(userName == null){
            userName = phone;
        }
        user.setUserName(userName);
        return user;
    }

    /**
     * 解析请求带来的参数信息,解析后封装成Comment对象返回给调用方法。
     * @param commentDetail 信息
     * @return
     */
    public static Comment parseComment(Map<String,String> commentDetail){
        Assert.notNull(commentDetail,"comment map is null");
        Comment comment = new Comment();
        comment.setLikes(0);
        String userComment = commentDetail.get("comment");
        if(!StringUtils.hasLength(userComment)){
            return null;
        }
        comment.setComment(userComment);
        Integer commentTypeNo = Integer.parseInt(commentDetail.get("type_no"));
        if(commentTypeNo == null){
            return null;
        }
        comment.setTypeNo(commentTypeNo);
        return comment;
    }

    /**
     *
     * @param typeNo
     * @return
     */
    public static Comment parseComment(Integer typeNo){
        Comment comment = new Comment();
        if(typeNo < 0 || typeNo > 10){
            return null;
        }
        comment.setTypeNo(typeNo);
        return comment;
    }

}
