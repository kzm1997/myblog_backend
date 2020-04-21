package com.kzm.blog.common.entity.User.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:43 2020/3/9
 * @Version
 */

@Data
public class UserEntityVo {
    private String account;

    private String password;

    private String avatar;

    private String email;

    private String sex;

    private String personLink;

    private LocalDate birthday;

    private String company;

    private String school;

    private String job;

    private String github;

    private String qq;

    private String nickname;

    private String motto;

    private String salt;

    private Integer status;

}
