package com.kzm.blog.common.entity.comment.bo;

import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 22:11 2020/4/6
 * @Version
 */
@Data

public class CommentSonBo {

    private Integer id;

    private Integer toId;

    private String content;

    private Integer parentId;

}
