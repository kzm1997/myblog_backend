package com.kzm.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 22:36 2020/2/28
 * @Version
 */
@SpringBootApplication
@MapperScan("com.kzm.blog.mapper")
public class KBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(KBlogApplication.class, args);
    }
}
