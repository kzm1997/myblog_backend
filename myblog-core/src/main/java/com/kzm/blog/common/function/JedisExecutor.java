package com.kzm.blog.common.function;

import io.lettuce.core.RedisConnectionException;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:54 2020/3/6
 * @Version
 */
@FunctionalInterface
public interface JedisExecutor<T,R> {
    R excute(T t)throws RedisConnectionException;
}
