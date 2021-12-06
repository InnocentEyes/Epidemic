package com.qzlnode.epidemic.miniprogram.pojo;

/**
 * <h2>用户的评论</h2>
 * @author qzlzzz
 */
public class Comment {

    private Integer commentId;

    private String userName;

    private String comment;

    private Integer Likes;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLikes() {
        return Likes;
    }

    public void setLikes(Integer likes) {
        Likes = likes;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                ", Likes=" + Likes +
                '}';
    }
}
