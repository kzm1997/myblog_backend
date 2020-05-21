package com.kzm.blog.common.entity.role;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:47 2020/5/3
 * @Version
 */
@Data
@TableName("role")
public class RoleEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "role_id",type= IdType.AUTO)
    private Integer roleId;

    private String roleName;

    private String pemark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
