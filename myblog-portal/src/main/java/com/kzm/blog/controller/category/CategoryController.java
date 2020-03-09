package com.kzm.blog.controller.category;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.bo.CategoryBo;
import com.kzm.blog.common.entity.category.vo.CategoryNameVo;
import com.kzm.blog.common.entity.category.vo.CategoryVo;
import com.kzm.blog.common.utils.MyPage;
import com.kzm.blog.service.article.ArticleService;
import com.kzm.blog.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 22:25 2020/3/1
 * @Version
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ArticleService articleService;

    @PostMapping("detail")
    @Log(module = "文章分类", operation = "获取所有文章分类详细")
    public Result<MyPage<CategoryVo>> listCategoryAll(@RequestBody() CategoryBo categoryBo) {
        MyPage<CategoryVo> myPage = categoryService.listCategoryById(categoryBo);
        return Result.success(myPage);
    }


    @GetMapping("getCategoryName")
    @Log(module = "文章分类",operation = "通过id获取文章分类名")
    public Result getCategoryName(@RequestParam() Integer id){
        CategoryEntity byId = categoryService.getById(id);
        return Result.success(new CategoryNameVo().setCategoryName(byId.getCategoryName()));
    }
}
