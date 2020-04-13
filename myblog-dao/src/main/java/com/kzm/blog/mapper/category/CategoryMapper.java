package com.kzm.blog.mapper.category;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.bo.CategoryBo;
import com.kzm.blog.common.entity.category.vo.CategoryNameVo;
import com.kzm.blog.common.entity.category.vo.CategoryShortVo;
import com.kzm.blog.common.entity.category.vo.CategoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:34 2020/3/1
 * @Version
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {


   IPage<CategoryVo> selectCategoryVoPage(@Param("page") Page<CategoryVo> page, @Param("categoryBo") CategoryBo categoryBo);

   List<CategoryShortVo> selectTagByparent(@Param("id")Integer id);

    List<CategoryNameVo> getHot();

    Integer getParentId(Integer id);

    int insertRelation(@Param("articleId") Integer articleId, @Param("tags") List<Integer> tags);

    /**
     * 获取文章的分类
     * @param articleId
     * @return
     */
    List<CategoryNameVo> selectByArticleId(@Param("articleId") Integer articleId);


    List<CategoryVo> selectArticles();

    int deleteArticle(@Param("id") Integer id);

}

