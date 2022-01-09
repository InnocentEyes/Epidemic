package com.qzlnode.epidemic.miniprogram.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.qzlnode.epidemic.miniprogram.dto.CommentView;

import javax.swing.text.View;
import java.util.Date;

/**
 * <h2>用户的评论</h2>
 * @author qzlzzz
 */
public class Comment {


    @JsonView({CommentView.class})
    private Integer commentId;

    @JsonView(CommentView.Detail.class)
    private Integer userId;

    @JsonView(CommentView.Detail.class)
    private String userName;

    @JsonView(CommentView.class)
    private Integer typeNo;

    @JsonView(CommentView.class)
    private Integer typeName;

    @JsonView(CommentView.class)
    private String comment;

    @JsonView(CommentView.class)
    private Date pubtime;

    @JsonView(CommentView.Detail.class)
    private Integer likes;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(Integer typeNo) {
        this.typeNo = typeNo;
    }

    public Date getPubtime() {
        return pubtime;
    }

    public void setPubtime(Date pubtime) {
        this.pubtime = pubtime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTypeName() {
        return typeName;
    }

    public void setTypeName(Integer typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", typeNo=" + typeNo +
                ", typeName=" + typeName +
                ", comment='" + comment + '\'' +
                ", pubtime=" + pubtime +
                ", likes=" + likes +
                '}';
    }
}
