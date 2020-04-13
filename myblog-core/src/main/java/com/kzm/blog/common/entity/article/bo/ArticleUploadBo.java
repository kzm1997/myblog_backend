package com.kzm.blog.common.entity.article.bo;

import lombok.Data;

import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 9:31 2020/3/30
 * @Version
 */
@Data
public class ArticleUploadBo {

    private Integer id;

    private String title;

    private String summary;

    private String category;

    private List<Integer> tags;

    private String content;

    private String contentHtml;
}
