package com.kzm.blog.common.entity.User.Bo;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:17 2020/3/9
 * @Version
 */
@Data
public class PassWdBo {

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "确定密码不能为空")
    private String confirmWd;

    @NotBlank(message = "新密码不能为空")
    private String newPassWd;
}
