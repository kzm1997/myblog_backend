package com.kzm.blog.common.entity.User.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:30 2020/3/12
 * @Version
 */
@Data
@Accessors(chain = true)
public class UserBaseInfoVo {

    private String nickname;

    private String avatar;

    private String email;

    private String sex;

    private String personLink;

    private String company;

    private String school;

    private String github;

    private String qq;

    private String city;

    private String motto;

    private LocalDate birthday;







}
