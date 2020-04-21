package com.kzm.blog.common.entity.User.Bo;

import com.kzm.blog.common.base.PageBo;
import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 16:16 2020/4/21
 * @Version
 */
@Data
public class UserBo  extends PageBo {

    private String nickname;

    private Integer status;
}
