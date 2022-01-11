package com.qzlnode.epidemic.miniprogram.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <h2>解析客户端传入的信息，并封装使用</h2>
 * @author qzlzzz
 */

public class ArgsHandler {

    private static final String USER_ID = "userId";

    private static final String USER_PASSWORD = "password";

    private static final String USER_NAME = "name";

    private static final String USER_PHONE = "phone";

    private static final String USER_COMMENT = "comment";

    private static final String COMMENT_TYPE_NO = "type_no";

    private static final String COMMENT_ID = "comment_id";

    private static final String PERSONAL_PROFILE = "profile";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    //private static final ReentrantLock LOCK = new ReentrantLock();

    /**
     *
     * @return
     */
    public static ArgsHandler build(){
        return new ArgsHandler();
    }

    public <T> T parse(Class<T> type,Map<String,String> map) throws JsonProcessingException {
        Assert.notEmpty(map,"message map no be null");
        if (type.isAssignableFrom(Comment.class)){
            return (T) toComment(map);
        }
        if(type.isAssignableFrom(User.class)){
            return (T) toUser(map);
        }
        return null;
    }

    public <T> T parse(Class<T> type,String message) throws JsonProcessingException {
        Assert.hasLength(message,"message detail no be null");
        if(type.isAssignableFrom(Comment.class)){
            return (T) toComment(message);
        }
        if(type.isAssignableFrom(User.class)){
            return (T) toUser(message);
        }
        return null;
    }
    
    /**
     * 解析User信息
     * @param userMessage 信息
     * @return 返回解析好的User对象
     * @throws JsonProcessingException 解析异常
     */
    protected User toUser(String userMessage) throws JsonProcessingException{
        JsonNode tree;
        tree = MAPPER.readTree(userMessage);
        if(MessageHolder.hasUser()){
            User user = new User();
            if(tree.hasNonNull(PERSONAL_PROFILE)){
                user.setProfile(tree.findValue(PERSONAL_PROFILE).asText());
            }
            if(tree.hasNonNull(USER_NAME)){
                user.setUserName(tree.findValue(USER_NAME).asText());
            }
            return user;
        }
        Iterator<String> fieldNames = tree.fieldNames();
        Map<String,String> map = new HashMap<>();
        while (fieldNames.hasNext()){
            String fieldName = fieldNames.next();
            String fieldValue = tree.findValue(fieldName).asText();
            map.put(fieldName,fieldValue);
        }
        return toUser(map);

    }

    /**
     *
     * @param userMessage
     * @return
     * @throws JsonProcessingException
     */
    protected User toUser(Map<String,String> userMessage) throws JsonProcessingException {
        if(MessageHolder.hasUser()){
            return toUser(MAPPER.writeValueAsString(userMessage));
        }
        if(userMessage.get(USER_PASSWORD) == null || userMessage.get(USER_PHONE) == null){
            throw new NullPointerException("password and phone must exist !");
        }
        User user = new User();
        user.setUserPhoneNumber(userMessage.get(USER_PHONE));
        user.setUserPassword(userMessage.get(USER_PASSWORD));
        if(userMessage.get(USER_NAME) != null){
            user.setUserName(USER_NAME);
        }
        return user;
    }


    /**
     * 解析请求带来的参数信息,解析后封装成Comment对象返回给调用方法。
     * @param commentDetail 信息
     * @return
     */
    protected Comment toComment(Map<String,String> commentDetail){
        Assert.notNull(commentDetail,"comment map is null");
        Comment comment = new Comment();
        if(commentDetail.get(COMMENT_ID) == null) {
            comment.setLikes(0);
        }else {
            comment.setCommentId(Integer.parseInt(commentDetail.get(COMMENT_ID)));
        }
        if(commentDetail.get(USER_COMMENT) != null){
            comment.setComment(commentDetail.get(USER_COMMENT));
        }
        if(commentDetail.get(COMMENT_TYPE_NO) != null){
            comment.setTypeNo(Integer.parseInt(commentDetail.get(COMMENT_TYPE_NO)));
        }
        return comment;
    }

    /**
     *
     * @param commentDetail
     * @return
     */
    protected Comment toComment(String commentDetail) throws JsonProcessingException {
        Map<String,String> map = new HashMap<>();
        JsonNode tree;
        tree = MAPPER.readTree(commentDetail);
        Iterator<String> fieldNames = tree.fieldNames();
        while (fieldNames.hasNext()){
            String fieldName = fieldNames.next();
            String fileValue = tree.findValue(fieldName).asText();
            map.put(fieldName,fileValue);
        }
        return toComment(map);
    }

}
