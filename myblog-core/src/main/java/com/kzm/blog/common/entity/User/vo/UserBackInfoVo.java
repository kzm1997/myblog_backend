package com.kzm.blog.common.entity.User.vo;

import lombok.Data;

import java.util.Set;

/**
 * @Author: kouzm
 * @Description: 用户后台登录返回信息
 * @Date: Created in 10:46 2020/4/15
 * @Version
 */
@Data
public class UserBackInfoVo {

    private UserInfoVo  userInfoVo;

    private Set<String> roles;

    private Set<String> permissions;

}
