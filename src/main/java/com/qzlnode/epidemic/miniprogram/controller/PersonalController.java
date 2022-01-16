package com.qzlnode.epidemic.miniprogram.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qzlnode.epidemic.miniprogram.dto.ResultView;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.result.Result;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import com.qzlnode.epidemic.miniprogram.service.PersonalService;
import com.qzlnode.epidemic.miniprogram.util.ArgsHandler;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import com.qzlnode.epidemic.miniprogram.result.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qzlzzz
 */
@RestController
public class PersonalController {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonalService personalService;

    @PostMapping(value = "/mobile/user/update",produces = MediaType.APPLICATION_JSON_VALUE)
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

    /**
     *
     * @return
     * @throws JsonProcessingException
     */
    @JsonView(ResultView.Detail.class)
    @GetMapping(value = "/mobile/user/getCord7",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getUserRecord7() throws JsonProcessingException {
        List<Comment> recordLists = personalService.getCommTypeCord7();
        return getCommentMessage(recordLists);
    }

    @JsonView(ResultView.Detail.class)
    @GetMapping(value = "/mobile/user/getDetail",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getUserRecordDtl7(){
        List<Comment> recordLists = personalService.getUserCommDtl();
        return getCommentMessage(recordLists);
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

    private Result getCommentMessage(List<? extends Comment> comments){
        MessageHolder.clearData();
        if (comments == null){
            logger.error("maybe the program is wrong");
            return new Result(Status.UNSUCCESSFUL.getCord(),Status.UNSUCCESSFUL.getReasonPhrase());
        }
        if(comments.size() == 0){
            logger.info("the user has not discussed the record in the last seven days");
            return new Result(Status.NO_RECORDS.getCord(),Status.NO_RECORDS.getReasonPhrase());
        }
        logger.info("start working on JSON conversion");
        return new Result(Status.SUCCESSFUL.getCord()
                ,Status.SUCCESSFUL.getReasonPhrase()
                ,comments.stream()
                .map(Object.class :: cast)
                .collect(Collectors.toList()));
    }
}
