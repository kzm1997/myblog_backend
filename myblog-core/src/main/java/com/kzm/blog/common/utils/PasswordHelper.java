package com.kzm.blog.common.utils;

import com.kzm.blog.common.entity.User.UserEntity;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: kouzm
 * @Description: 生成随机salt 生成md5密码
 * @Date: Created in 10:36 2020/3/5
 * @Version
 */

public class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static String algorithmName = "md5";

    private static final int hashIterations = 2;

    /**
     * 保存新密码
     * @param userEntity
     */
    public static void encryptPassword(UserEntity userEntity) {
        userEntity.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(algorithmName, userEntity.getPassword(),
                ByteSource.Util.bytes(userEntity.getSalt()), hashIterations).toHex();
        userEntity.setPassword(newPassword);
    }

    /**
     * 加密秘密,并获取加密的密码
     *  @param userEntity
     * @return
     */
    public static String authenticatPassword(UserEntity userEntity){
        String password=new SimpleHash(algorithmName,userEntity.getPassword(),userEntity.getSalt(),hashIterations).toHex();
        return password;
    }
}
