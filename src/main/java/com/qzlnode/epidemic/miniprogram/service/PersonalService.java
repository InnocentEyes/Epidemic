package com.qzlnode.epidemic.miniprogram.service;

import com.qzlnode.epidemic.miniprogram.pojo.Comment;
import com.qzlnode.epidemic.miniprogram.pojo.CommentType;
import com.qzlnode.epidemic.miniprogram.pojo.User;

import java.util.List;

/**
 * @author qzlzzz
 * @since 2022/1/6
 */
public interface PersonalService {

    /**
     * <h3>更新用户的信息</h3>
     * @param user
     * @return
     */
    boolean changeMessage(User user);

    /**
     *
     * @return
     */
    List<Comment> getCommTypeCord7();

    /**
     *
     * @return
     */
    List<Comment> getUserCommDtl();
}
