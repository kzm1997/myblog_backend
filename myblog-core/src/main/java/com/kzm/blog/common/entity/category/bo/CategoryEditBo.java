package com.kzm.blog.common.entity.category.bo;

import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 13:49 2020/5/2
 * @Version
 */
@Data
public class CategoryEditBo {

    private Integer id;

    private String  categoryName;

    private String message;

    private String avatar;

    private Integer parentId;
}
