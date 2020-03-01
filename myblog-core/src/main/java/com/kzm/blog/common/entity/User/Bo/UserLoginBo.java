package com.kzm.blog.common.entity.User.Bo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:37 2020/2/28
 * @Version
 */
@Data
public class UserLoginBo {

    @NotBlank
    @Length(max = 10,message = "账号长度不能超过10位")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;
}
