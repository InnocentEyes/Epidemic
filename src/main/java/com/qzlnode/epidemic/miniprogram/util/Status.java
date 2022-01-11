package com.qzlnode.epidemic.miniprogram.util;

/**
 * @author qzlzzz
 */
public enum Status {
    /**
     * 操作不成功
     */
    UNSUCCESSFUL(-1,"{\"message\": \"fail\", \"reason\": \"current operation unsuccessful\"}"),

    /**
     * 操作成功
     */
    SUCCESSFUL(1,"{\"message\": \"success\", \"reason\": \"current operation successful\"}"),

    /**
     * 
     */
    LOGIN_UNSUCCESSFUL(-1,"{\"message\": \"operation fail\", \"reason\": \"maybe phone or password error\"}"),

    /**
     * 
     */
    LOGIN_SUCCESSFUL(1,"{\"message\": \"login success\", \"reason\": \"user login success,the effective time is 30 minutes\"}"),

    /**
     * 
     */
    USERID_TOO_SMALL(-1,"{\"message\": \"operation fail\", \"reason\": \"the user id must larger than 0,but not equal 0\"}"),

    /**
     * 
     */
    TYPENO_TOO_SMALL(-1,"{\"message\": \"operation fail\", \"reason\": \"the comment type number too small\"}"),

    /**
     * 
     */
    PAGE_SIZE_SMALL(-1,"{\"message\": \"operation fail\", \"reason\": \"the page size must larger than 0\"}"),

    /**
     * 
     */
    PAFE_SIZE_LARGE(-1,"{\"message\": \"operation fail\", \"reason\": \"the page size too larger\"}"),

    /**
     *
     */
    NO_RECORDS(2,"{\"message\": \"operation fail\", \"reason\": \"uer has not commented for seven days\"}");
    
    private final Integer code;
    
    private final String reasonPhrase;

    public String getReasonPhrase() {
        return reasonPhrase.replace("\\","");
    }

    public Integer getCord(){
        return code;
    }

    private Status(Integer code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }
}
