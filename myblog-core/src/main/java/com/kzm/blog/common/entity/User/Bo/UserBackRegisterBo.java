package com.kzm.blog.common.entity.User.Bo;


import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:27 2020/4/21
 * @Version
 */
@Data
public class UserBackRegisterBo {

    private Integer id;

    private String account;

    private String password;

    private String nickname;

    private Integer roleId;

    private String email;

    private Integer status;

    private String avatar;

}
