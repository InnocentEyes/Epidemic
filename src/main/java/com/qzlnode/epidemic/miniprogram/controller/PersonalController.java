package com.qzlnode.epidemic.miniprogram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qzlnode.epidemic.miniprogram.dto.ResultView;
import com.qzlnode.epidemic.miniprogram.pojo.Result;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.PersonalService;
import com.qzlnode.epidemic.miniprogram.util.ArgsHandler;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import com.qzlnode.epidemic.miniprogram.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qzlzzz
 */
@RestController
public class PersonalController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonalService personalService;

    @PostMapping(value = "/mobile/usr/update",produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(ResultView.class)
    public Result updateUser(@RequestBody(required = false) String userMessage) throws JsonProcessingException {
        User user = ArgsHandler.build().parse(User.class,userMessage);
        boolean target = personalService.changeMessage(user);
        MessageHolder.clearData();
        if(!target){
            return new Result(Status.UNSUCCESSFUL.getCord(),Status.UNSUCCESSFUL.getReasonPhrase());
        }
        return new Result(Status.SUCCESSFUL.getCord(),Status.SUCCESSFUL.getReasonPhrase());
    }

    @JsonView(ResultView.Detail.class)
    @GetMapping(value = "/mobile/user/getCord7",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getUserRecord7() throws JsonProcessingException {
        

        return null;
    }



    @ExceptionHandler({
        IllegalArgumentException.class,
            JsonProcessingException.class
    })
    public String handlerError(HttpServletRequest request, Exception ex){
        logger.error("handler the " + request.getRequestURI() + " error, \n "+
                "because of "+ex.getMessage());
        return Status.UNSUCCESSFUL.getReasonPhrase();
    }
}
