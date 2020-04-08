package com.kzm.blog.common.entity.comment.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 21:12 2020/4/6
 * @Version
 */
@Data
@Accessors(chain = true)
public class CommentSonVo {

    private Integer id;

    private Integer commentId;

    private Integer fromId;

    private String fromName;

    private String fromAvatar;

    private Integer toId;

    private String toName;


    private  String toAvatar;

    private String content;

    private LocalDateTime date;

}

