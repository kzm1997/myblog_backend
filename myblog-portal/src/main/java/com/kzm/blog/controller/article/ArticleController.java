package com.kzm.blog.controller.article;

import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.entity.article.bo.ArticleShortBo;
import com.kzm.blog.common.entity.article.bo.ArticleUploadBo;
import com.kzm.blog.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 16:20 2020/3/10
 * @Version
 */
@RestController()
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getArticles")
    @Log(module = "文章模块", operation = "获取指定分类下的文章")
    public Result getArticles(ArticleShortBo articleShortBo) {
        return articleService.getArticleByCategory(articleShortBo);
    }

    @GetMapping("{id}")
    @Log(module = "文章模块", operation = "获取文章内容")
    public Result getArtice(@PathVariable("id") Integer id) {
        return articleService.getArticle(id);
    }

    @PostMapping("publish")
    @Log(module = "文章模块", operation = "发布文章")
    public Result publishArticle(@RequestBody ArticleUploadBo articleUploadBo) {
        return articleService.publishArticle(articleUploadBo);
    }

    @PostMapping("update")
    @Log(module = "文章模块", operation = "修改文章")
    public Result updateArtice( ArticleUploadBo articleUploadBo) {
        return articleService.updateArticle(articleUploadBo);
    }

    @GetMapping("/delte/{id}")
    @Log(module = "文章模块", operation = "删除文章")
    public Result delteArtice(@PathVariable("id") Integer id) {
        return articleService.delteArticle(id);
    }

    @GetMapping("getRecommend")
    @Log(module = "文章模块",operation = "获取推介文章")
    public Result getRecommend(){
        return  articleService.getRecommend();
    }
}
