package com.qzlnode.epidemic.miniprogram.util;

/**
 * <h2>响应信息</h2>
 * <p>响应状态码</p>
 * @author qzlzzz
 */
public enum Status {
    /**
     * 该状态码表示用户操作出错，或者是用户登录出错。
     */
    ERROR_CODE{
        public String getStatus(){
            return "{code: 300,message: error}";
        }
    },
    /**
     *  该状态码表示用户操作是成功的。
     */
    TRUE_CODE{
        public String getStatus(){
            return "{code: 200,message: true}";
        }
    };
    public abstract String getStatus();
}
