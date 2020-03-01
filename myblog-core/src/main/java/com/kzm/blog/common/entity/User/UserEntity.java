package com.kzm.blog.common.entity.User;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kzm.blog.common.base.BaseEntity;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:08 2020/2/28
 * @Version
 */
@Data
@TableName("sys_user")
public class UserEntity extends BaseEntity {

    private String account;

    private String password;

    private String avatar;

    private String email;

    private String sex;

    private String personLink;

    private LocalDate birthday;

    private String company;

    private String school;

    private String github;

    private String qq;

    private String nickname;

    private String motto;

    private String salt;

    private Integer status;

}
