package com.kzm.blog.common.entity.menu;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:51 2020/5/3
 * @Version
 */
@Data
@TableName("menu")
public class MenuEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type= IdType.AUTO)
    private Integer menuId;

    private Integer parentId;

    private String menuName;

    private String path;

    private String component;

    private String perms;

    private String icon;

    private Integer type;

    private  Integer orderNum;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
