package com.kzm.blog.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kzm.blog.common.entity.User.vo.UserFerralVo;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.article.bo.ArticleShortBo;
import com.kzm.blog.common.entity.article.vo.ArticleShortVo;
import com.kzm.blog.common.entity.category.vo.CategoryShortVo;
import com.kzm.blog.common.entity.log.vo.Recent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {

    IPage<ArticleShortVo>  selectShortByTag(@Param("page") Page<ArticleShortVo> page, @Param("articleShortBo") ArticleShortBo articleShortBo);

    int selectCountLikeByUId(@Param("user_id") Integer id);

    int addComments(@Param("id") Integer id);

    CategoryShortVo selectCategory(@Param("id") Integer id);

    List<CategoryShortVo> selectTags(@Param("id") Integer id);

    List<UserFerralVo> getWordCount(@Param("ids")List<Integer> collect);


    int updateReadNum(@Param("id") String id);

    List<Recent> getRecentArticle();
}
