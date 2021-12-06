package com.qzlnode.epidemic.miniprogram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.util.MessageUtil;
import com.qzlnode.epidemic.miniprogram.util.ParseUtil;
import com.qzlnode.epidemic.miniprogram.util.SessionHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class IndexController {


    @PostMapping(value = "/login",produces = "application/json;charset=utf-8")
    public String login(@RequestBody String userMessage,HttpSession session) throws JsonProcessingException {
        if(userMessage == null){
            return MessageUtil.ERROR_CODE.getStatus();
        }
        User user = ParseUtil.parseUser(userMessage);
        return MessageUtil.TRUE_CODE.getStatus();
    }
}
