package com.kzm.blog.common.entity.User.Bo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 14:01 2020/3/10
 * @Version
 */
@Data
public class UserMaterialBo {

    private String nickname;

    private String sex;

    private String motto;

    private String personLink;

    private String school;

    private String company;

    private String job;

    private LocalDate birthday;

}
