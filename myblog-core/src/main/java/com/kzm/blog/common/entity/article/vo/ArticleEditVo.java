package com.kzm.blog.common.entity.article.vo;

import com.kzm.blog.common.entity.category.vo.CategoryShortVo;
import lombok.Data;

import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:06 2020/4/9
 * @Version
 */
@Data
public class ArticleEditVo {

    private Integer id;

    private String title;

    private String summary;

    private String content;

    private CategoryShortVo category;

    private List<CategoryShortVo> tags;
}
