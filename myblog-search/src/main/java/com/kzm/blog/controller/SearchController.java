package com.kzm.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.article.vo.ArticleShortVo;
import com.kzm.blog.common.entity.category.vo.CategoryNameVo;
import com.kzm.blog.common.entity.category.vo.CategoryShortVo;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.mapper.EsKBlogRepository;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.article.EsArticle;
import com.kzm.blog.mapper.article.ArticleMapper;
import com.kzm.blog.mapper.category.CategoryMapper;
import com.kzm.blog.mapper.user.UserMapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:43 2020/4/20
 * @Version
 */
@RestController
public class SearchController {

    @Autowired
    EsKBlogRepository esBlogRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("search")
    public Result search(@RequestParam("keyword") String keyword) {
        QueryStringQueryBuilder builder = QueryBuilders.queryStringQuery(keyword);
        Iterable<EsArticle> search = esBlogRepository.search(builder);
        ArrayList<EsArticle> esArticles = Lists.newArrayList(search);
        if (esArticles==null||esArticles.size()==0){
              throw new KBlogException("没有相关内容");
        }
        List<ArticleShortVo> collect = esArticles.stream().map(esArticle -> {
            ArticleEntity articleEntity = articleMapper.selectById(esArticle.getId());
            UserEntity userEntity = userMapper.selectById(articleEntity.getAuthorId());
            ArticleShortVo articleShortVo = new ArticleShortVo();
            List<CategoryNameVo> categoryNameVos = categoryMapper.selectByArticleId(articleEntity.getId());
            List<CategoryShortVo> categoryShortVos = categoryNameVos.stream().map(nameVo -> {
                CategoryShortVo temp = new CategoryShortVo();
                BeanUtil.copyProperties(nameVo, temp);
                return temp;
            }).collect(Collectors.toList());
            articleShortVo.setCategorys(categoryShortVos)
                    .setId(articleEntity.getId())
                    .setReadNum(articleEntity.getReadNum())
                    .setCommentNum(articleEntity.getCommentNum())
                    .setAuthorId(articleEntity.getAuthorId())
                    .setDescription(articleEntity.getDescription())
                    .setNickname(userEntity.getNickname())
                    .setAvatar(userEntity.getAvatar())
                    .setTitle(articleEntity.getTitle())
                    .setPublish(articleEntity.getPublish())
                    .setDescription(articleEntity.getDescription())
                    .setUpdateTime(articleEntity.getUpdateTime());
            return articleShortVo;
        }).collect(Collectors.toList());
        List<ArticleShortVo>list=new ArrayList<>();
        for (ArticleShortVo articleShortVo : collect) {
              if (articleShortVo.getPublish().equals("1")){
                  list.add(articleShortVo);
              }
        }
        return Result.success(list);
    }

}
