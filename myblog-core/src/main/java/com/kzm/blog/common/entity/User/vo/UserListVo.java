package com.kzm.blog.common.entity.User.vo;

import lombok.Data;

import java.time.LocalDate;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 16:25 2020/4/21
 * @Version
 */
@Data
public class UserListVo {

    private Integer id;

    private String nickname;

    private String email;

    private String avatar;

    private Integer articles;

    private Integer status;

    private LocalDate updateTime;

}
