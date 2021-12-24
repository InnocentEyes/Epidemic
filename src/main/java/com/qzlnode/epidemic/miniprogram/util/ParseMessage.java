package com.qzlnode.epidemic.miniprogram.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.Province;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <h2>解析客户端传入的信息，并封装使用</h2>
 * @author qzlzzz
 */
public class ParseMessage {

    private static final String USER_PASSWORD = "password";

    private static final String USER_NAME = "name";

    private static final String USER_PHONE = "phone";

    private static final String USER_COMMENT = "comment";

    private static final String COMMENT_TYPE_NO = "type_no";

    /**
     * 解析User信息
     * @param userMessage 信息
     * @return 返回解析好的User对象
     * @throws JsonProcessingException 解析异常
     */
    public static User ToUser(String userMessage) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = null;
        User user = new User();
        try {
            tree = mapper.readTree(userMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return user;
        }
        if(!tree.hasNonNull(USER_PHONE) || !tree.hasNonNull(USER_PASSWORD)){
            throw new NullPointerException("user message must contain phone and password");
        }
        user.setUserPhoneNumber(tree.get(USER_PHONE).asText());
        user.setUserPassword(tree.get(USER_PASSWORD).asText());
        if(tree.hasNonNull(USER_NAME)){
            user.setUserName(tree.get(USER_NAME).asText());
            return user;
        }
        return user;
    }

    /**
     * 解析请求带来的参数信息,解析后封装成Comment对象返回给调用方法。
     * @param commentDetail 信息
     * @return
     */
    public static Comment ToComment(Map<String,String> commentDetail){
        Assert.notNull(commentDetail,"comment map is null");
        Comment comment = new Comment();
        comment.setLikes(0);
        String userComment = commentDetail.get(USER_COMMENT);
        if(!StringUtils.hasLength(userComment)){
            return null;
        }
        comment.setComment(userComment);
        Integer commentTypeNo = Integer.parseInt(commentDetail.get(COMMENT_TYPE_NO));
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
    public static Comment ToComment(Integer typeNo){
        Comment comment = new Comment();
        if(typeNo < 0){
            return null;
        }
        comment.setTypeNo(typeNo);
        return comment;
    }

}
