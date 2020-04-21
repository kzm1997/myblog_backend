package com.kzm.blog.common.utils;

import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.constants.Base;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.common.function.CacheSelector;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:11 2020/3/6
 * @Version
 */
@Slf4j
public class KblogUtils {


    /**
     * 将token解密
     *
     * @param encryptToken 加密的token
     * @return 解密后的token
     */
    public static String decryptToken(String encryptToken) {
        try {
            EncryptUtil encryptUtil = new EncryptUtil(Base.TOKEN_CACHE_PREFIX);
            return encryptUtil.decrypt(encryptToken);
        } catch (Exception e) {
            log.info("token解密失败: ", e);
            return null;
        }
    }

    /**
     * 将token加密
     *
     * @param token
     * @return 加密后的token
     */
    public static String encryptToken(String token) {
        try {
            EncryptUtil encryptUtil = new EncryptUtil(Base.TOKEN_CACHE_PREFIX);
            return encryptUtil.encrypt(token);
        } catch (Exception e) {
            log.info("token加密失败: ", e);
            return null;
        }
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static String getUserName() {
        HttpServletRequest httpServeltRequest = HttpContextUtils.getHttpServeltRequest();
        String headerToken = httpServeltRequest.getHeader(Base.TOKEN);
        if (StringUtils.isBlank(headerToken)) {
            return null;
        }
        String decryptToken = KblogUtils.decryptToken(headerToken);
        String userName = JWTUtil.getAccount(decryptToken);
        return userName;
    }

    /**
     * 查询缓存模板
     *
     * @param cacheSelector    查询缓存的方法
     * @param databaseSelector 数据库查询方法
     * @param <T>
     * @return
     */
    public static <T> T selectCacheByTemplate(CacheSelector<T> cacheSelector, Supplier<T> databaseSelector) {
        try {
            log.debug("query data from redis...");
            //先查redis缓存
            T t = cacheSelector.select();
            if (t == null) {
                return databaseSelector.get();
            } else {
                return t;
            }
        } catch (Exception e) {
            //查询缓存出错,则去数据库查询
            log.error("redis error:",e);
            log.debug("query data from database....");
            return databaseSelector.get();
        }
    }

}
