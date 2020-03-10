package com.kzm.blog.common.entity.User.Bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:17 2020/3/9
 * @Version
 */
@Data
public class PassWdBo {

    @NotBlank
    private String password;

    @NotBlank
    private String confirmWd;

    @NotBlank
    private String newPassWd;
}
