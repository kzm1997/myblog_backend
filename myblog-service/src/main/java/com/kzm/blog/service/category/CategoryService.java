package com.kzm.blog.service.category;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.bo.CategoryBo;
import com.kzm.blog.common.entity.category.vo.CategoryVo;
import com.kzm.blog.common.utils.MyPage;

import java.util.List;

public interface CategoryService extends IService<CategoryEntity> {

    MyPage<CategoryVo> listCategoryById(CategoryBo categoryBo);

    /**
     * 获取首页文章分类
     * @return
     */
    Result getIndexCategory();

    /**
     * 首页分类标签
     * @param id
     * @return
     */
    Result getTag(Integer id);

    /**
     * 获取热门标签
     * @return
     */
    Result getHot();

    /**
     * 获取分类信息
     * @param id
     * @return
     */
    Result getMessage(Integer id);

    /**
     * 获取分类饼图
     * @return
     */
    Result getCategorySys();

}
