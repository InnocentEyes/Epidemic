package com.qzlnode.epidemic.miniprogram.pojo;

import java.util.Date;

/**
 * <h2>用户的评论</h2>
 * @author qzlzzz
 */
public class Comment {

    private Integer commentId;

    private Integer userId;

    private String userName;

    private Integer typeNo;

    private String comment;

    private Date pubtime;

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

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", typeNo=" + typeNo +
                ", comment='" + comment + '\'' +
                ", pubtime=" + pubtime +
                ", likes=" + likes +
                '}';
    }
}
