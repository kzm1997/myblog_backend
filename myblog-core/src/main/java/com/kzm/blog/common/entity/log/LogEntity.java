package com.kzm.blog.common.entity.log;


import com.baomidou.mybatisplus.annotation.TableName;
import com.kzm.blog.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 22:09 2020/2/26
 * @Version
 */
@Data
@TableName("log")
public class LogEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2222L;


    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "方法")
    private String method;

    @ApiModelProperty(value = "模块")
    private String module;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "操作")
    private String operation;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "花费时间")
    private long time;

    @ApiModelProperty(value = "用户id")
    private String userId;


}
