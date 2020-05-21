package com.kzm.blog.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.mapper.category.CategoryMapper;
import com.kzm.blog.service.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:57 2020/3/9
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void  tres1(){
        Jedis jedis = jedisPool.getResource();
        String set = jedis.set("kzm", "kzmtest");
        System.out.println(set);
    }


}
