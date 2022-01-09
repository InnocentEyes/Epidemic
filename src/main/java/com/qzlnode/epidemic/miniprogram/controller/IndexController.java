package com.qzlnode.epidemic.miniprogram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.IndexService;
import com.qzlnode.epidemic.miniprogram.util.ArgsHandler;
import com.qzlnode.epidemic.miniprogram.util.Security;
import com.qzlnode.epidemic.miniprogram.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody(required = false) String userMessage, HttpServletResponse response) throws JsonProcessingException {
        if(userMessage == null){
            logger.error("login :user message is null");
            return new ResponseEntity<>(Status.UNSUCCESSFUL.getReasonPhrase(),HttpStatus.METHOD_NOT_ALLOWED);
        }
        logger.info("user message detail is {}",userMessage);
        User user = ArgsHandler.build().parse(User.class,userMessage);
        user = indexService.loginService(user);
        if(user.getId() != null) {
            logger.info("user login acc");
            String token = Security.getToken(user);
            response.setHeader("Access-Control-Expose-Headers", "user_token");
            response.setHeader("user_token", token);
            return new ResponseEntity<>(user.toString(),HttpStatus.OK);
        }
        logger.info("user named: "+user.getUserName()+" login error");
        return new ResponseEntity<>(Status.UNSUCCESSFUL.getReasonPhrase(),HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     *
     * @param userMessage
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody(required = false) String userMessage) throws JsonProcessingException{
        if(!StringUtils.hasLength(userMessage)){
            logger.error("register : user message is null");
            //没有用户信息
            return Status.UNSUCCESSFUL.getReasonPhrase();
        }
        User user = ArgsHandler.build().parse(User.class,userMessage);
        boolean target = indexService.registerService(user);
        if(!target){
            //已有账户或已经登录
            return Status.UNSUCCESSFUL.getReasonPhrase();
        }
        //登录成功
        return Status.SUCCESSFUL.getReasonPhrase();
    }

    /**
     *
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "user info is missing !")
    @ExceptionHandler(
            {NullPointerException.class,
            JsonProcessingException.class})
    public String handlerError(HttpServletRequest request,NullPointerException ex){
        logger.error("parseUser error, the detail is :"+ex.getMessage());
        return Status.UNSUCCESSFUL.getReasonPhrase();
    }

}
