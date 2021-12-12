package com.qzlnode.epidemic.miniprogram.service.Impl;

import com.qzlnode.epidemic.miniprogram.dao.mysql.CommentDao;
import com.qzlnode.epidemic.miniprogram.dao.redis.Impl.RedisForComment;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.service.CommentService;
import com.qzlnode.epidemic.miniprogram.util.ReturnValueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qzlzzz
 */
@Service
public class CommentServiceImpl implements CommentService {

    private Logger logger = LoggerFactory.getLogger(getClass());//日志

    @Autowired
    private RedisForComment redis;//

    @Autowired
    private CommentDao commentDao;//

    /**
     *
     * @param comment
     * @return
     */
    @Override
    public boolean sendComment(Comment comment) {
        if(commentDao.addComment(comment)){
            logger.info("system send comment to mysql acc");
            redis.set(comment);
            return true;
        }
        return false;
    }

    /**
     *
     * @param comment
     * @return
     */
    @Override
    public List<String> getCommentsByNo(Comment comment) {
        String[] res = redis.get(comment);
        if(res != null){
            logger.info("get the comments in redis");
            List<String> userComments = ReturnValueHandler.handlerReturnValue(res,Comment.class);
            return userComments;
        }
        List<Comment> comments = commentDao.findComment(comment);
        if(comments.size() == 0){
            logger.info("this type no : {} , has no comment in mysql",comment.getTypeNo());
            return null;
        }
        for (Comment tmp : comments) {
            redis.set(tmp);
        }
        logger.info("set comment to redis,type no : {}",comment.getTypeNo());
        res = comments.toArray(new String[comments.size()]);
        return ReturnValueHandler.handlerReturnValue(res,Comment.class);
    }
}
