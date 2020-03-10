package com.kzm.blog.common.entity.User.Bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 13:46 2020/3/10
 * @Version
 */
@Data
public class ExMaterialBo {

    @NotBlank
    private String github;

    @NotBlank
    private String qq;
}
