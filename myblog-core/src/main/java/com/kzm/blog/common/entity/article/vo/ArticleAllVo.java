package com.kzm.blog.common.entity.article.vo;


import com.kzm.blog.common.entity.category.vo.CategoryShortVo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:36 2020/4/23
 * @Version
 */
@Data
public class ArticleAllVo {

    private Integer id;

    private LocalDateTime updateTime;

    private String title;

    private String content;

    private String nickname;

    private String description;

    private Integer recommend;

    private Integer readNum;

    private Integer publish;

    private Integer commentNum;

    private List<CategoryShortVo> categorys;
}
