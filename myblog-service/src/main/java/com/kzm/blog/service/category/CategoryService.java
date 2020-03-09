package com.kzm.blog.service.category;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.bo.CategoryBo;
import com.kzm.blog.common.entity.category.vo.CategoryVo;
import com.kzm.blog.common.utils.MyPage;

import java.util.List;

public interface CategoryService extends IService<CategoryEntity> {

    MyPage<CategoryVo> listCategoryById(CategoryBo categoryBo);
}
