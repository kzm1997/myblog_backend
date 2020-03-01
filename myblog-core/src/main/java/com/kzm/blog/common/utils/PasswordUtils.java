package com.kzm.blog.common.utils;

import com.kzm.blog.common.entity.User.UserEntity;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:07 2020/2/29
 * @Version
 */
public class PasswordUtils {

    private static RandomNumberGenerator randomNumberGenerator=new SecureRandomNumberGenerator();

    private static String alogorithmName="md5";

    private static String algorithmName = "md5";
    private static final int hashIterations = 2;

    public static void encryptPassword(UserEntity user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }

}
