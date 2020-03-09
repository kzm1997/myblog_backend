package com.kzm.blog.common.entity.article;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kzm.blog.common.base.BaseEntity;
import lombok.Data;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:12 2020/3/1
 * @Version
 */
@TableName("article")
@Data
public class ArticleEntity  extends BaseEntity {

    private String title;

    private String description;

    private Integer authorId;

    private String content;

    private String contentFormat;

    private Integer readNum;

    private Integer commentNum;

    private Integer likeNum;

    private String publish;

    private String categoryId;


}
