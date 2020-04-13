package com.kzm.blog.common.entity.User.vo;

import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 12:25 2020/4/5
 * @Version
 */
@Data
public class UserAuthorVo {

    private Integer id;

    private String nickname;

    private String avatar;

    private String qq;

    private String github;

    private String city;

    private String job;

    private Boolean like;

}
