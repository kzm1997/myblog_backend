package com.kzm.blog.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.article.bo.ArticleShortBo;
import com.kzm.blog.common.entity.article.vo.ArticleShortVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {

    IPage<ArticleShortVo>  selectShortByTag(@Param("page") Page<ArticleShortVo> page, @Param("articleShortBo") ArticleShortBo articleShortBo);

    int selectCountLikeByUId(@Param("user_id") Integer id);
}
