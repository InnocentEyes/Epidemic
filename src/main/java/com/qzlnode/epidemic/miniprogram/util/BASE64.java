package com.qzlnode.epidemic.miniprogram.util;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author qzlzzz
 * 加密工具类
 */
public class BASE64 {

    private final static Base64.Encoder encoder = Base64.getEncoder();
    private final static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 给字符串加密
     * @param password
     * @return
     */
    public static String encode(String password) {
        byte[] textByte = new byte[0];
        try {
            textByte = password.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedPassword = encoder.encodeToString(textByte);
        return encodedPassword;
    }

    /**
     * 将加密后的字符串进行解密
     * @param encodedPassword
     * @return
     */
    public static String decode(String encodedPassword) {
        String password = null;
        try {
            password = new String(decoder.decode(encodedPassword), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return password;
    }
}