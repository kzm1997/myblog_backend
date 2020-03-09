package com.kzm.blog.service.redis;

import com.kzm.blog.common.exception.RedisException;
import io.lettuce.core.RedisConnectionException;

/**
 * @Author: kouzm
 * @Description:  封装常用的redis命令
 * @Date: Created in 15:38 2020/3/6
 * @Version
 */
public interface RedisService {


    /**
     * get命令
     * @param key
     * @return
     * @throws RedisConnectionException
     */
   String get(String key)throws RedisException;

    /**
     * del命令
     * @param key
     * @return
     * @throws RedisConnectionException
     */
   Long del(String... key)throws RedisException;

    /**
     * set命令
     * @param key
     * @param value
     * @return
     * @throws RedisConnectionException
     */
   String set(String key,String value)throws RedisException;



   String set(String key,String value,Long milliscends)throws RedisException;

    /**
     *  pexpire命令
     * @param key
     * @param milliscends 毫秒
     * @return
     * @throws RedisException
     */
  Long pexpire(String key,Long milliscends)throws RedisException;
}
