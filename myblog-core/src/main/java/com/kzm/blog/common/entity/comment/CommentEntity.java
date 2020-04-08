package com.kzm.blog.common.entity.comment;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kzm.blog.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 18:26 2020/4/6
 * @Version
 */
@Data
@TableName("comment")
@Accessors(chain = true)
public class CommentEntity  implements Serializable {


    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type=IdType.AUTO)
    private Integer id;


    private Integer ownerId;  //文章id

    private Integer fromId;   //评论者id

    private Integer likeNum;

    private String content;

    @TableField("to_id")
    private Integer told; //被评论者id

    private Integer parentId;  //父评论id


    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private LocalDateTime createTime;


}
