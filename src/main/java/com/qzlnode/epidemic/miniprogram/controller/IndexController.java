package com.qzlnode.epidemic.miniprogram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.IndexService;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import com.qzlnode.epidemic.miniprogram.util.ParseMessage;
import com.qzlnode.epidemic.miniprogram.util.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qzlzzz
 */
@RestController
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IndexService indexService;

    /**
     *
     * @param userMessage
     * @param response
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/login",produces = "application/json;charset=utf-8")
    public ResponseEntity<String> login(@RequestBody String userMessage, HttpServletResponse response) throws JsonProcessingException {
        if(userMessage == null){
            logger.error("login :user message is null");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = ParseMessage.parseUser(userMessage);
        user = indexService.LoginService(user);
        if(user.getId() != null) {
            logger.info("user login acc");
            String token = Security.getToken(user);
            response.setHeader("Access-Control-Expose-Headers", "user_token");
            response.setHeader("user_token", token);
            return new ResponseEntity<>(user.toString(),HttpStatus.FOUND);
        }
        logger.info("user named"+user.getUserName()+"login error");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param userMessage
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/register",produces = "application/json;charset=utf-8")
    public ResponseEntity<String> register(@RequestBody String userMessage) throws JsonProcessingException {
        if(!StringUtils.hasLength(userMessage)){
            logger.error("register : user message is null");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//没有用户信息
        }
        User user = ParseMessage.parseUser(userMessage);
        boolean target = indexService.registerService(user);
        if(!target){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//已有账户或已经登录
        }
        return new ResponseEntity<>(HttpStatus.OK);//登录成功
    }
}
