package com.kzm.blog.controller.category;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.bo.CategoryBo;
import com.kzm.blog.common.entity.category.vo.CategoryNameVo;
import com.kzm.blog.common.entity.category.vo.CategoryShortVo;
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

    /**
     * 文章分类详细
     * @param categoryBo
     * @return
     */
    @GetMapping("detail")
    public Result<MyPage<CategoryVo>> listCategoryAll(CategoryBo categoryBo) {
        MyPage<CategoryVo> myPage = categoryService.listCategoryById(categoryBo);
        return Result.success(myPage);
    }


    @GetMapping("getCategoryName")
    public Result getCategoryName(@RequestParam() Integer id) {
        CategoryEntity byId = categoryService.getById(id);
        return Result.success(new CategoryNameVo().setCategoryName(byId.getCategoryName()));
    }

    /**
     * 首页文章分类
     * @return
     */
    @GetMapping("/index")
    public Result getCategory() {
        return categoryService.getIndexCategory();
    }

    /**
     * 首页分类tag
     * @param id
     * @return
     */
    @GetMapping("/tagByCategory")
    public Result getTag(@RequestParam("categoryId") Integer id) {
        return categoryService.getTag(id);
    }

    /**
     * 获取所有文章分类
     * @return
     */
    @GetMapping("getAllCategory")
    public Result getAllCategory() {
        List<CategoryEntity> list = categoryService.list(new QueryWrapper<CategoryEntity>().lambda().eq(CategoryEntity::getParentId, -1));
        return Result.success(list);
    }

    /**
     * 获取热门标签
     * @return
     */
    @GetMapping("getHot")
    public Result getCategoryHot() {
        return categoryService.getHot();
    }

    /**
     * 获取分类信息
     * @param id
     * @return
     */
    @GetMapping("CategoryMessage")
    public Result getCategoryMessage(@RequestParam("id")Integer id) {
       return  categoryService.getMessage(id);
    }


}
