package com.qzlnode.epidemic.miniprogram.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qzlnode.epidemic.miniprogram.pojo.User;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * <h2>生成token,验证token</h2>
 * @author qzlzzz
 * @since 2021/12/7 晚上22:14
 */
public class Security {

    private static final long EFFECTIVE_TIME = 1000 * 60 * 30;//有效时间为30分钟

    private static final String TOKEN_KEY = UUID.randomUUID().toString();

    /**
     * <h3>生成token,便于验证,权限检验等</h3>
     * @param user user message
     * @return token
     */
    public static String getToken(User user){
        Assert.notNull(user,"object in tokenUtil is null");
        Integer userId = user.getId();
        if(userId == 0){
            throw new IllegalArgumentException("userId is null");
        }
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withClaim("login_status",true)
                .withClaim("create_time",new Date())
                .withClaim("user_id",userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EFFECTIVE_TIME))
                .sign(Algorithm.HMAC256(TOKEN_KEY));
        return token;
    }

    /**
     *<h3>验证token的操作</h3>
     * <p>
     *     验证成功返回true
     *     验证失败返回false
     * </p>
     * @param token the token
     * @return {@code true} or {@code false}
     */
    public static boolean parseToken(String token){
       if(!StringUtils.hasLength(token)){
           throw new IllegalArgumentException("parse token need token");
       }
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_KEY)).build();
       try {
           DecodedJWT verify = verifier.verify(token);
           return true;
       }catch (SignatureVerificationException exception){
            return false;
       }
    }

}