package com.qzlnode.epidemic.miniprogram.service;

import com.qzlnode.epidemic.miniprogram.pojo.Comment;

import java.util.List;

/**
 * @author qzlzzz
 */
public interface CommentService {

    /**
     *
     * @param comment
     * @return
     */
    boolean sendComment(Comment comment);

    /**
     *
     * @param comment
     * @return
     */
    List<String> getCommentsByNo(Comment comment);
}
