package com.kzm.blog.common.entity.article.vo;

import com.kzm.blog.common.entity.User.TimeComparator;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: kouzm
 * @Description:  我最近发布的文章
 * @Date: Created in 12:01 2020/5/17
 * @Version
 */
@Data
public class ArticleRecentVo extends TimeComparator {

    private Integer id;

    private String title;

    private String summary;

    private Integer readNum;

    private Integer commentNum;

    private String type;

    private String comment;


}
