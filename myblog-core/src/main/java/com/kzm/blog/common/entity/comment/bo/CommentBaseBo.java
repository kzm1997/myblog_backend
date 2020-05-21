package com.kzm.blog.common.entity.comment.bo;

import com.kzm.blog.common.base.PageBo;
import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 18:49 2020/4/6
 * @Version
 */
@Data
public class CommentBaseBo extends PageBo {

    private Integer id; //文章id

    private String content;

    private Integer ownerId;


}
