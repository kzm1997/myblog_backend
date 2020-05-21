package com.kzm.blog.common.entity.article;

import com.kzm.blog.common.base.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 18:33 2020/5/20
 * @Version
 */
@Data
@Document(indexName = "blog",type = "_doc",useServerConfiguration = true,createIndex = false)
public class EsArticle {

    @Id
    @Field(type = FieldType.Text,store = true)
    private String id;

    private String title;

    private String description;

    private Integer authorId;

    private String content;

    private String contentFormat;

    private Integer readNum;

    private Integer commentNum;

    private Integer likeNum;

    private String publish;

    private Integer recommend;
}
