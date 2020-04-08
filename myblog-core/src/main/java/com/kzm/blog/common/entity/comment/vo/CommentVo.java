package com.kzm.blog.common.entity.comment.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 20:58 2020/4/6
 * @Version
 */
@Data
@Accessors(chain = true)
public class CommentVo {

    private Integer id;

    private String content;

    private LocalDateTime date;

    private Integer fromId;

    private String fromAvatar;

    private String fromName;

    private Integer toId;

    private String toName;

    private boolean Like;

    private String toAvatar;

    private Integer likeNum;

    private List<CommentSonVo> reply;
}
