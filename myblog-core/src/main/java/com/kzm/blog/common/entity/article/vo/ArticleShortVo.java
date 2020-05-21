package com.kzm.blog.common.entity.article.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kzm.blog.common.entity.category.vo.CategoryShortVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:14 2020/3/10
 * @Version
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ArticleShortVo {

    private Integer id;

    private String title;

    private String description;

    private Integer readNum;

    private Integer commentNum;


    private Integer authorId;

    private List<CategoryShortVo> categorys;

    private LocalDateTime updateTime;

    private String avatar;

    private String nickname;
    @JsonIgnore
    private String publish;
}
