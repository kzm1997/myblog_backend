package com.kzm.blog.service.redis.Impl;

import com.kzm.blog.common.exception.RedisException;
import com.kzm.blog.common.function.JedisExecutor;
import com.kzm.blog.service.redis.RedisService;
import io.lettuce.core.RedisConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:39 2020/3/6
 * @Version
 */
@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    JedisPool jedisPool;

    /**
     * 处理jedis请求
     *
     * @param j   处理逻辑,通过lambda行为参数化
     * @param <T>
     * @return 处理结果
     * @throws RedisConnectionException
     */
    private <T> T excuteByJedis(JedisExecutor<Jedis, T> j) throws RedisException {
        try (Jedis jedis = jedisPool.getResource()) {
            return j.excute(jedis);
        } catch (Exception e) {
            throw new RedisConnectionException(e.getMessage());
        }
    }

    @Override
    public String get(String key) throws RedisException {
        return this.excuteByJedis(j -> j.get(key));
    }

    @Override
    public Long del(String... key) throws RedisException {
        return this.excuteByJedis(j -> j.del(key));
    }

    @Override
    public String set(String key, String value) throws RedisException {
        return this.excuteByJedis(j -> j.set(key, value));
    }

    @Override
    public String set(String key, String value, Long milliscends) throws RedisException {
        String result = this.set(key, value);
        this.pexpire(key, milliscends);
        return result;
    }

    @Override
    public Long pexpire(String key, Long milliscends) throws RedisException {
        return this.excuteByJedis(j -> j.pexpire(key, milliscends));
    }
}
