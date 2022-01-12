package com.qzlnode.epidemic.miniprogram.service.Impl;

import com.qzlnode.epidemic.miniprogram.dao.mysql.CommentDao;
import com.qzlnode.epidemic.miniprogram.dao.redis.Impl.RedisForCommentType;
import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.service.CommentService;
import com.qzlnode.epidemic.miniprogram.util.MessageHolder;
import com.qzlnode.epidemic.miniprogram.util.ReturnValueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author qzlzzz
 */
@Service
public class CommentServiceImpl implements CommentService {

    /**
     *
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisForCommentType redis;

    @Autowired
    private CommentDao commentDao;

    /**
     *
     * @param comment
     * @return
     */
    @Override
    public boolean sendComment(Comment comment) {
        comment.setUserId(MessageHolder.getUserId());
        if(commentDao.addComment(comment)){
            logger.info("system send comment to mysql acc");
            return redis.set(comment);
        }
        return false;
    }

    /**
     *
     * @param comment
     * @return
     */
    @Override
    public List<Comment> getCommentsByNo(Comment comment) {
        String[] res = redis.get(comment);
        if(res != null){
            logger.info("get the comments in redis");
            List<Comment> userComments = ReturnValueHandler.handlerReturnValue(res,Comment.class);
            return userComments;
        }
        List<Comment> comments = commentDao.findComment(comment);
        if(comments == null || comments.size() == 0){
            logger.info("this type no : {} , has no comment in mysql",comment.getTypeNo());
            return null;
        }
//        for (Comment tmp : comments) {
//            redis.set(tmp);
//        }
        comments.stream()
                .filter(element -> element.getCommentId() != null)
                .forEach(element -> {
                    element.setPubtime(new Date());
                    redis.set(element);
                });
        logger.info("set comment to redis,type no : {}",comment.getTypeNo());
        res = comments.toArray(new String[comments.size()]);
        return ReturnValueHandler.handlerReturnValue(res,Comment.class);
    }

    /**
     *
     * @param comment comment对象
     * @return
     */
    @Override
    public boolean addLikes(Comment comment) {
        if(!redis.update(comment) || !commentDao.updateLikes(comment)){
            logger.error("update comment {} likes in comment type number {} error !",
                    comment.getCommentId(),
                    comment.getTypeNo());
            return false;
        }
        return true;
    }
}
