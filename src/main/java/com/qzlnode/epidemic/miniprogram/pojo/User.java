package com.qzlnode.epidemic.miniprogram.pojo;

/**
 * <h2>用户类</h2>
 * @author qzlzzz
 */
public class User {
    private Integer id;
    private String userPhoneNumber;
    private String userName;
    private String userPassword;
    private String profile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"userPhoneNumber\":\"" + userPhoneNumber + '\"' +
                ", \"userName\":\"" + userName + '\"' +
                '}';
    }
}
