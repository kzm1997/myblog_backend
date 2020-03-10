package com.kzm.blog.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kzm.blog.common.properties.KBlogProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:41 2020/3/5
 * @Version
 */
@Slf4j
public class JWTUtil {

    private static final long EXPIRE_TIME = SpringContextUtil.getBean(KBlogProperties.class).getJwtTimeOut()*1000;

    /**
     * 验证token是否正确
     *
     * @param token    密钥
     * @param account  账号
     * @param password 密码
     * @return
     */
    public static boolean verify(String token, String account, String password) {
        Algorithm algorithm = Algorithm.HMAC256(password);
        try {
            JWTVerifier verifier = JWT.require(algorithm).withClaim("account", account).build();
            verifier.verify(token);
            log.info("token is valid");
            return true;
        } catch (Exception e) {
            log.error("token is invalid{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从token中获取用户名
     *
     * @param token
     * @return token中包含的用户名
     */
    public static String getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("account").asString();
        } catch (JWTDecodeException e) {
            log.error("error: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成token
     *
     * @param account
     * @param password
     * @return
     */
    public static String sign(String account, String password) {

        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(password);  //用密码做密钥
            return JWT.create().withClaim("account", account).withExpiresAt(date).sign(algorithm);
        } catch (Exception e) {
            log.error("error: {}", e);
            return null;
        }

    }
}
