package com.qzlnode.epidemic.miniprogram.dao.mysql;

import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qzlzzz
 */
@Mapper
public interface CommentDao {
    /**
     *
     * @param comment
     * @return
     */
    boolean addComment(Comment comment);

    /**
     *
     * @param comment
     * @return
     */
    List<Comment> findComment(Comment comment);
}
