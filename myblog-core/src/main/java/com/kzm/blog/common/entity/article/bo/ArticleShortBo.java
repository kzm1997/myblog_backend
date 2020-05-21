package com.kzm.blog.common.entity.article.bo;

import com.kzm.blog.common.base.PageBo;
import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:35 2020/3/11
 * @Version
 */
@Data
public class ArticleShortBo extends PageBo {

    /**
     * 分类id
     */
    private Integer id;

    private String nickname;

    private Integer publish;
}
