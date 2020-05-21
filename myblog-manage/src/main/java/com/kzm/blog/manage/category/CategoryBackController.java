package com.kzm.blog.manage.category;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.bo.CategoryBo;
import com.kzm.blog.common.entity.category.bo.CategoryEditBo;
import com.kzm.blog.common.utils.MyPage;
import com.kzm.blog.service.category.CategoryService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 14:00 2020/4/14
 * @Version
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryBackController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    //@RequiresPermissions("category:view")
    public Result getCategory(CategoryBo categoryBo) {
        Page<CategoryEntity> page = (Page<CategoryEntity>) categoryService.page(new Page<>(categoryBo.getPageNum(), categoryBo.getPageSize()),
                new QueryWrapper<CategoryEntity>().lambda().eq(CategoryEntity::getParentId, categoryBo.getId()));
        MyPage<CategoryEntity> myPage = new MyPage<CategoryEntity>().getMyPage(page);
        return Result.success(myPage);
    }

    @PostMapping("/add")
    //@RequiresPermissions("category:add")
    public Result deleteCategory(@RequestBody CategoryEditBo categoryEditBo) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtil.copyProperties(categoryEditBo, categoryEntity);
        boolean b = categoryService.save(categoryEntity);
        if (b) {
            return Result.success();
        }
        return Result.error(ResultCode.DATA_INSERT_ERR);
    }

    @PostMapping("/edit")
    //@RequiresPermissions("categroy:edit")
    public Result editCategory(@RequestBody CategoryEditBo categoryEditBo) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtil.copyProperties(categoryEditBo, categoryEntity);
        boolean b = categoryService.update(categoryEntity, new QueryWrapper<CategoryEntity>().lambda()
                .eq(CategoryEntity::getId, categoryEditBo.getId()));
        if (b) {
            return Result.success();
        }
        return Result.error(ResultCode.DATA_INSERT_ERR);
    }

    @GetMapping("/delete")
    //@RequiresPermissions("category:delete")
    public Result deleteCategory(@RequestParam("id") Integer id){
        boolean flag = categoryService.remove(new QueryWrapper<CategoryEntity>().lambda().eq(CategoryEntity::getId, id));
        if (flag){
            return Result.success();
        }
        return Result.error(ResultCode.DATA_DELTE_ERR);
    }


}
