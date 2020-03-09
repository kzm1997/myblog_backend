package com.kzm.blog.common.entity.User.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:32 2020/3/9
 * @Version
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserInfoVo {

    private String token;

    private UserEntityVo userEntityVo;
}
