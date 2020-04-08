package com.kzm.blog.common.entity.article.vo;

import com.kzm.blog.common.entity.User.vo.UserAuthorVo;
import com.kzm.blog.common.entity.category.vo.CategoryNameVo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 14:51 2020/3/30
 * @Version
 */
@Data
public class ArticleViewVo {

    private LocalDateTime updateTime;

    private String title;

    private Integer readNum;

    private String content;

    private UserAuthorVo authorVo;

    private List<CategoryNameVo> tags;

}
