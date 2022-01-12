package com.qzlnode.epidemic.miniprogram.service;

import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.List;

/**
 * @author qzlzzz
 */
public interface CommentService {

    /**
     * <h3>发送评论</h3>
     * @param comment
     * @return
     */
    boolean sendComment(Comment comment);

    /**
     *  <h3>根据话题的种类获取讨论</h3>
     * @param comment
     * @return
     */
    List<Comment> getCommentsByNo(Comment comment);

    /**
     * <h3>根据言论的id值增加点赞</h3>
     * @param comment comment对象
     * @return {@code false} or {@code true}
     */
    boolean addLikes(Comment comment);
}
