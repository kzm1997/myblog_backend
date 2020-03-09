package com.kzm.blog.common.base;

import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:01 2020/3/3
 * @Version
 */
@Data
public class PageBo {

    private Integer pageNum;

    private Integer pageSize;

    private String sort;

    private String sortBy;
}
