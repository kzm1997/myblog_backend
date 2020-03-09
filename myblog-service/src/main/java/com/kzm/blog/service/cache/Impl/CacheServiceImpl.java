package com.kzm.blog.service.cache.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kzm.blog.common.constants.Base;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.exception.RedisException;
import com.kzm.blog.service.cache.CacheService;
import com.kzm.blog.service.redis.RedisService;
import com.kzm.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: kouzm
 * @Description: 缓存业务
 * @Date: Created in 15:22 2020/3/6
 * @Version
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void saveUser(String account) throws RedisException, JsonProcessingException {
        QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(UserEntity::getAccount,account);
        //todo 需要关联得到用户权限信息
        UserEntity userEntity=userService.getOne(wrapper);
        this.deleteUser(account);
        redisService.set(Base.cache.User_CACHE_PREFIX+account,mapper.writeValueAsString(userEntity));
    }

    @Override
    public void deleteUser(String account) throws RedisException {
        redisService.del(Base.cache.User_CACHE_PREFIX+account);
    }
}
