package com.kzm.blog.common.entity.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:11 2020/4/8
 * @Version
 */
@Data
@TableName("comment_user_like")
@Accessors(chain = true)
public class CommentUserLikeEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField("comment_id")
    private Integer commentId;

    @TableField("user_id")
    private Integer userId;

    @TableField("like_time")
    private LocalDateTime likeTime;

    private Integer status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentUserLikeEntity entity = (CommentUserLikeEntity) o;
        return Objects.equals(commentId, entity.commentId) &&
                Objects.equals(userId, entity.userId) &&
                Objects.equals(status, entity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, userId, status);
    }
}
