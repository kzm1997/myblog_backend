package com.kzm.blog.common.entity.category.vo;

import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 16:03 2020/3/2
 * @Version
 */
@Data
public class CategoryVo {


    private Integer id;

    private String categoryName;

    private String avatar;

    //分类下的文章数
    private Integer articles;
}
