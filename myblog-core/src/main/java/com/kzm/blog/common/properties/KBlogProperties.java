package com.kzm.blog.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: kouzm
 * @Description: 博客系统属性配置
 * @Date: Created in 9:52 2020/3/6
 * @Version
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "kblog")
public class KBlogProperties {

    //无须登录就可以访问的接口

    private String annonUrl;

    //token默认有效时间
    private Long jwtTimeOut = 86400L;
}
