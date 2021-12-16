package com.qzlnode.epidemic.miniprogram.controller;

import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.service.CommentService;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import com.qzlnode.epidemic.miniprogram.util.ParseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author qzlzzz
 */
@RestController
public class CommentController {

    private Logger logger = LoggerFactory.getLogger(getClass());//日志

    @Autowired
    private CommentService commentService;

    /**
     *
     * @param commentDetail
     * @return
     */
    @PostMapping(value = "/send",produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus sendComment(@RequestParam Map<String,String> commentDetail){
        Comment comment = ParseMessage.ToComment(commentDetail);
        if(comment == null){
            logger.info("user "+MessageHolder.getUserId()+"send null comment or system send null typeNo");
            return HttpStatus.BAD_REQUEST;
        }
        boolean target = commentService.sendComment(comment);
        MessageHolder.clearData();
        if(target){
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_ACCEPTABLE;
    }

    /**
     *
     * @param typeId
     * @return
     */
    @GetMapping(value = "/getInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getComments(@RequestParam(value = "typeId",required = true) Integer typeId){
        Comment comment = ParseMessage.ToComment(typeId);
        if(comment == null){
            logger.info("system send the typeId to lager");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        List<String> commentsByNo = commentService.getCommentsByNo(comment);
        MessageHolder.clearData();
        if(commentsByNo == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(commentsByNo,HttpStatus.OK);
    }

}
