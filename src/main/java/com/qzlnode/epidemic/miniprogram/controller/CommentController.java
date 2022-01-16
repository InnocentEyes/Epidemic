package com.qzlnode.epidemic.miniprogram.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qzlnode.epidemic.miniprogram.dto.ResultView;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.CommentType;
import com.qzlnode.epidemic.miniprogram.result.Result;
import com.qzlnode.epidemic.miniprogram.service.CommentService;
import com.qzlnode.epidemic.miniprogram.service.CommentTypeService;
import com.qzlnode.epidemic.miniprogram.util.ArgsHandler;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import com.qzlnode.epidemic.miniprogram.result.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qzlzzz
 */
@RestController
public class CommentController {

    /**
     *
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentTypeService commentTypeService;

    /**
     *
     * @param commentDetail
     * @return
     */
    @PostMapping(value = "/mobile/send",produces = MediaType.APPLICATION_JSON_VALUE)
    public String sendComment(@RequestParam(required = false) Map<String,String> commentDetail) throws JsonProcessingException {
        Comment comment = ArgsHandler.build().parse(Comment.class,commentDetail);
        if(comment.getTypeNo() <= 0 || comment.getComment() == null){
            logger.info("user "+MessageHolder.getUserId()+"send null comment or system send null typeNo");
            MessageHolder.clearData();
            return Status.UNSUCCESSFUL.getReasonPhrase();
        }
        boolean target = commentService.sendComment(comment);
        MessageHolder.clearData();
        if(target){
            return Status.SUCCESSFUL.getReasonPhrase();
        }
        return Status.UNSUCCESSFUL.getReasonPhrase();
    }

    /**
     * 此接口未写完
     * @param typeNo
     * @return
     */
    @JsonView(ResultView.Detail.class)
    @GetMapping(value = "/mobile/comment/getInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getComments(@RequestParam(required = false) Map<String,String> typeNo) throws JsonProcessingException {
        Comment comment = ArgsHandler.build().parse(Comment.class,typeNo);
        if(comment.getTypeNo() == null || comment.getTypeNo() <= 0){
            return new Result(Status.COMMENTNO_NULL.getCord(),Status.COMMENTNO_NULL.getReasonPhrase());
        }
        List<Comment> commentsByNo = commentService.getCommentsByNo(comment);
        MessageHolder.clearData();
        if(commentsByNo == null) {
            return new Result(Status.NO_RECORDS.getCord(),Status.NO_RECORDS.getReasonPhrase());
        }
        return new Result(Status.SUCCESSFUL.getCord(),
                Status.SUCCESSFUL.getReasonPhrase(),
                commentsByNo.stream()
                        .map(Object.class::cast)
                        .collect(Collectors.toList()));
    }

    /**
     *
     * @param pn
     * @return
     */
    @JsonView(ResultView.Detail.class)
    @GetMapping(value = "/mobile/types",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getTypes(@RequestParam(value = "count",required = false,defaultValue = "1") Integer pn){
        if(pn < 1){
            logger.error("the page count is smaller than one, record at {}",new Date());
            return new Result(Status.PAGE_SIZE_SMALL.getCord(),Status.PAGE_SIZE_SMALL.getReasonPhrase());
        }
        MessageHolder.clearData();
        Page<CommentType> typePage = new Page<>(pn, 5);
        Page<CommentType> page = commentTypeService.page(typePage, null);
        List<CommentType> records = page.getRecords();
        if( records == null ||  records.size() == 0){
            logger.error("there is no records in mysql");
            return new Result(Status.UNSUCCESSFUL.getCord(),Status.UNSUCCESSFUL.getReasonPhrase());
        }
        logger.info("get the message : {} acc",records);
        return new Result(Status.SUCCESSFUL.getCord(),Status.SUCCESSFUL.getReasonPhrase(),records.stream().map(Object.class :: cast).collect(Collectors.toList()));
    }

    /**
     *
     * @param commentId
     * @return
     */
    @JsonView(ResultView.class)
    @GetMapping(value = "/mobile/likes",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result sendLikes(@RequestParam(required = false) Map<String,String> commentId) throws JsonProcessingException {
        Comment comment = ArgsHandler.build().parse(Comment.class,commentId);
        if(comment.getTypeNo() == null || comment.getCommentId() == null){
            return new Result(Status.COMMENTNO_OR_TYPENO_NULL.getCord(),
                    Status.COMMENTNO_OR_TYPENO_NULL.getReasonPhrase());
        }
        if(comment.getComment() != null){
            comment.setComment(null);
        }
        boolean target = commentService.addLikes(comment);
        MessageHolder.clearData();
        if(!target){
            return new Result(Status.UNSUCCESSFUL.getCord(),
                    Status.UNSUCCESSFUL.getReasonPhrase());
        }
        return new Result(Status.SUCCESSFUL.getCord(),
                Status.SUCCESSFUL.getReasonPhrase());
    }

    /**
     *
     * @param request
     * @param ex
     */
    @ResponseStatus(code = HttpStatus.FORBIDDEN,reason = "typeNo or commentId must not be empty")
    @ExceptionHandler({
            NullPointerException.class,
            JsonProcessingException.class
    })
    public String handlerError(HttpServletRequest request,Exception ex){
        MessageHolder.clearData();
        logger.error("handler the "+request.getRequestURI()+" error, \n " +
                "because of "+ex.getMessage());
        return Status.UNSUCCESSFUL.getReasonPhrase();
    }
}
