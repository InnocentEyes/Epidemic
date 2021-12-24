package com.qzlnode.epidemic.miniprogram.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.CommentType;
import com.qzlnode.epidemic.miniprogram.pojo.Result;
import com.qzlnode.epidemic.miniprogram.service.CommentService;
import com.qzlnode.epidemic.miniprogram.service.CommentTypeService;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import com.qzlnode.epidemic.miniprogram.util.ParseMessage;
import com.qzlnode.epidemic.miniprogram.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public String sendComment(@RequestParam Map<String,String> commentDetail){
        Comment comment = ParseMessage.ToComment(commentDetail);
        if(comment == null){
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
     *
     * @param typeId
     * @return
     */
    @GetMapping(value = "/mobile/comment/getInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getComments(@RequestParam(value = "typeId",required = false) Integer typeId){
        if(typeId == null){
            throw new NullPointerException("must contain typeId");
        }
        Comment comment = ParseMessage.ToComment(typeId);
        if(comment == null){
            logger.info("system send the typeId to lager");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        List<String> commentsByNo = commentService.getCommentsByNo(comment);
        MessageHolder.clearData();
        if(commentsByNo == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentsByNo,HttpStatus.OK);
    }

    @GetMapping(value = "/mobile/types",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<CommentType> getTypes(@RequestParam(value = "count",required = false) Integer pn){
        if(pn < 1){
            logger.error("the page count is smaller than one, record at {}",new Date());
            return new Result<>(Status.UNSUCCESSFUL,null);
        }
        MessageHolder.clearData();
        Page<CommentType> typePage = new Page<>(pn, 5);
        Page<CommentType> page = commentTypeService.page(typePage, null);
        List<CommentType> records = page.getRecords();
        if( records == null ||  records.size() == 0){
            logger.error("there is no records in mysql");
            return new Result<>(Status.UNSUCCESSFUL,null);
        }
        logger.info("get the message : {} acc",records);
        return new Result<>(Status.SUCCESSFUL,page.getRecords());
    }

    @GetMapping(value = "/mobile/likes",produces = MediaType.APPLICATION_JSON_VALUE)
    public String sendLikes(){
        MessageHolder.clearData();
        return Status.SUCCESSFUL.getReasonPhrase();
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN,reason = "TypeId must not be empty")
    @ExceptionHandler(NullPointerException.class)
    public void handlerError(HttpServletRequest request,Exception ex){
        MessageHolder.clearData();
        logger.error("handler the "+request.getRequestURI()+" error, \n" +
                "because"+ex.getMessage());
    }
}
