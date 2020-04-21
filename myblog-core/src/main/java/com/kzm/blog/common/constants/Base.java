package com.kzm.blog.common.constants;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:43 2020/2/29
 * @Version
 */
public class Base {

    public static final String TOKEN = "Authentication";

    //token缓冲前缀
    public static final String TOKEN_CACHE_PREFIX = "kblog.cache.token";







    public interface user {
        /**
         * 用户状态
         */
        Integer STATUS_VALID=0; //正常

        Integer STATUS_LOCK=1;  //锁定

        /**
         * 性别
         */
        String SEX_MALE="0";

        String SEX_FEMALE="1";

        String SEX_UNKNOW="2";

        String DEFAULT_AVATAR="/default.png";  //默认头像
    }

    public interface cache{
        //user缓存前缀
        String User_CACHE_PREFIX="kBlog.user.";

        //在线用户的zset前缀
        String ACTIVE_USERS_ZSET_PREFIX="Kblog.user.active";
    }
}
