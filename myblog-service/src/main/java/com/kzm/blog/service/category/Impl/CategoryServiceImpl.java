package com.kzm.blog.service.category.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.bo.CategoryBo;
import com.kzm.blog.common.entity.category.vo.CategoryVo;
import com.kzm.blog.common.utils.MyPage;
import com.kzm.blog.mapper.category.CategoryMapper;
import com.kzm.blog.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 22:29 2020/3/1
 * @Version
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;



    /**
     * 分页查询categoryid下的类
     * * @param categoryBo
     * @return
     */
    @Override
    public MyPage<CategoryVo> listCategoryById(CategoryBo categoryBo) {
        Page<CategoryVo> page=new Page<>(categoryBo.getPageNum(),categoryBo. getPageSize());
        page = (Page<CategoryVo>) categoryMapper.selectCategoryVoPage(page, categoryBo);
        return new MyPage<CategoryVo>().getMyPage(page);
    }


}
