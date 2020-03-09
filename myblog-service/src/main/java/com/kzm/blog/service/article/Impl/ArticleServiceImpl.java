package com.kzm.blog.service.article.Impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.mapper.article.ArticleMapper;
import com.kzm.blog.service.article.ArticleService;
import org.springframework.stereotype.Service;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 16:08 2020/3/1
 * @Version
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,ArticleEntity> implements ArticleService {

}
