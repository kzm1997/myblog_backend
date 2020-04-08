package com.kzm.blog.common.entity.comment.bo;

import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 14:21 2020/4/8
 * @Version
 */
@Data
public class LikeBo {

    //评论id
    private Integer commentId;

    //0取消点赞 1点赞
    private Integer type;
}
