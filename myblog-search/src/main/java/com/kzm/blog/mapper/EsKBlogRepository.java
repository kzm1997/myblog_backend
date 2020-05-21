package com.kzm.blog.mapper;

import com.kzm.blog.common.entity.article.EsArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 19:37 2020/5/20
 * @Version
 */
@Component
public interface EsKBlogRepository extends ElasticsearchRepository<EsArticle,String> {

}
