package com.kzm.blog.service.cache;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kzm.blog.common.exception.RedisException;

public interface CacheService {

    /**
     * 缓存用户信息
     * @param account
     * @throws Exception
     */
    void saveUser(String account) throws RedisException, JsonProcessingException;


    /**
     * 删除用户信息
     * @param account
     * @throws Exception
     */
    void deleteUser(String account)throws RedisException;
}
