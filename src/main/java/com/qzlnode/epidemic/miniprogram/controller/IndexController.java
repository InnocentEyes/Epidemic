package com.qzlnode.epidemic.miniprogram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.IndexService;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import com.qzlnode.epidemic.miniprogram.util.ParseMessage;
import com.qzlnode.epidemic.miniprogram.util.Security;
import com.qzlnode.epidemic.miniprogram.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<String,Object> login(@RequestBody String userMessage, HttpServletResponse response) throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        if(userMessage == null){
            logger.error("login :user message is null");
            map.put("error",Status.ERROR_CODE.getStatus());
            return map;
        }
        User user = ParseMessage.parseUser(userMessage);
        MessageHolder.setUser(user);
        user = indexService.LoginService(user);
        MessageHolder.clearData();
        if(user.getId() != null) {
            logger.info("user login acc");
            String token = Security.getToken(user);
            response.setHeader("Access-Control-Expose-Headers", "user_token");
            response.setHeader("user_token", token);
            map.put("acc",Status.TRUE_CODE.getStatus());
            map.put("user",user.toString());
            return map;
        }
        map.put("error",Status.ERROR_CODE.getStatus());
        logger.info("user named"+user.getUserName()+"login error");
        return map;
    }

    /**
     *
     * @param userMessage
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/register",produces = "application/json;charset=utf-8")
    public String register(@RequestBody String userMessage) throws JsonProcessingException {
        if(!StringUtils.hasLength(userMessage)){
            logger.error("register : user message is null");
            return Status.ERROR_CODE.getStatus();//没有用户信息
        }
        User user = ParseMessage.parseUser(userMessage);
        MessageHolder.setUser(user);
        boolean target = indexService.registerService(user);
        MessageHolder.clearData();
        if(!target){
            return Status.ERROR_CODE.getStatus();//已有账户或已经登录
        }
        return Status.TRUE_CODE.getStatus();//登录成功
    }
}
