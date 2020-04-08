package com.kzm.blog.common.entity.comment.bo;

import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 18:49 2020/4/6
 * @Version
 */
@Data
public class CommentBaseBo {

    private Integer id; //文章id

    private String content;

    private Integer ownerId;


}
