package com.kzm.blog.common.entity.log;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:07 2020/4/13
 * @Version
 */
@TableName("login_log")
@Data
public class LoginLog {

    private String account;

    private LocalDateTime loginTime;

    private String location;

    private String ip;
}
