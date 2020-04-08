package com.kzm.blog.common.entity.category.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:19 2020/3/10
 * @Version
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryShortVo {

    private Integer id;

    private String categoryName;

    private Integer parentId;
}
