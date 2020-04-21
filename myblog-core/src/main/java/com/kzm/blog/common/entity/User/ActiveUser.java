package com.kzm.blog.common.entity.User;

import cn.hutool.core.date.DateUtil;
import com.kzm.blog.common.utils.TimeUtils;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: kouzm
 * @Description: 在线用户
 * @Date: Created in 17:29 2020/4/14
 * @Version
 */
@Data
public class ActiveUser implements Serializable {

    //唯一编码
    private String id=RandomStringUtils.randomAlphanumeric(20);

    //用户账号
    private String account;

    //ip地址
    private String ip;

    //token(加密后)
    private String token;

    //登录时间
    private String loginTime=TimeUtils.LocalDateTimeToString(LocalDateTime.now(),TimeUtils.FULL_TIME_SPLIT_PATTERN);

    //登录地点
    private String loginAddress;
}
