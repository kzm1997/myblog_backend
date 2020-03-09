package com.kzm.blog.common.utils;

import com.kzm.blog.common.constants.Base;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:11 2020/3/6
 * @Version
 */
@Slf4j
public class KblogUtils {


    /**
     * token 解密
     * @param encryptToken 加密的token
     * @return 解密后的token
     */
    public static  String decryptToken(String encryptToken){
        try {
            EncryptUtil encryptUtil=new EncryptUtil(Base.TOKEN_CACHE_PREFIX);
            return  encryptUtil.decrypt(encryptToken);
        } catch (Exception e) {
            log.info("token解密失败: ",e);
            return  null;
        }
    }

    /**
     *  token 加密
     * @param token
     * @return 加密后的token
     */
    public static String encryptToken(String token){
        try {
            EncryptUtil encryptUtil=new EncryptUtil(Base.TOKEN_CACHE_PREFIX);
            return encryptUtil.encrypt(token);
        } catch (Exception e) {
            log.info("token解密失败: ",e);
            return null;
        }
    }


}
