package com.qzlnode.epidemic.miniprogram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qzlnode.epidemic.miniprogram.dto.ResultView;
import com.qzlnode.epidemic.miniprogram.result.Result;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.IndexService;
import com.qzlnode.epidemic.miniprogram.util.ArgsHandler;
import com.qzlnode.epidemic.miniprogram.util.Security;
import com.qzlnode.epidemic.miniprogram.result.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author qzlzzz
 */
@RestController
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IndexService indexService;

    /**
     * @param userMessage
     * @param response
     * @return
     * @throws JsonProcessingException
     */
    @JsonView(ResultView.Detail.class)
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result login(@RequestBody(required = false) String userMessage, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException {
        if (request.getHeader("token") != null) {
            return new Result(Status.SUCCESSFUL.getCord(), Status.SUCCESSFUL.getReasonPhrase());
        }
        if (userMessage == null) {
            logger.error("login :user message is null");
            return new Result(Status.LOGIN_UNSUCCESSFUL.getCord(),Status.LOGIN_UNSUCCESSFUL.getReasonPhrase());
        }
        logger.info("user message detail is {}",userMessage);
        User user = ArgsHandler.build().parse(User.class,userMessage);
        user = indexService.loginService(user);
        if (user.getId() == null) {
            logger.info("user named :" + user.getUserName() + " login error");
            return new Result(Status.LOGIN_UNSUCCESSFUL.getCord(), Status.LOGIN_UNSUCCESSFUL.getReasonPhrase());
        }
        String token = Security.getToken(user);
        response.setHeader("Access-Control-Expose-Headers", "user_token");
        response.setHeader("user_token", token);
        return new Result(Status.LOGIN_SUCCESSFUL.getCord(),
                Status.LOGIN_SUCCESSFUL.getReasonPhrase(),
                Collections.singleton(user).stream().map(Object.class::cast).collect(Collectors.toList()));
    }

    /**
     * @param userMessage
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody(required = false) String userMessage) throws JsonProcessingException {
        if (!StringUtils.hasLength(userMessage)) {
            logger.error("register : user message is null");
            //没有用户信息
            return Status.UNSUCCESSFUL.getReasonPhrase();
        }
        User user = ArgsHandler.build().parse(User.class, userMessage);
        boolean target = indexService.registerService(user);
        if (!target) {
            //已有账户或已经登录
            return Status.UNSUCCESSFUL.getReasonPhrase();
        }
        //登录成功
        return Status.SUCCESSFUL.getReasonPhrase();
    }

    /**
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "user info is missing !")
    @ExceptionHandler(
            {NullPointerException.class,
                    JsonProcessingException.class})
    public String handlerError(HttpServletRequest request, NullPointerException ex) {
        logger.error("parseUser error, the detail is :" + ex.getMessage());
        return Status.UNSUCCESSFUL.getReasonPhrase();
    }

}
