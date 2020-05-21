package com.kzm.blog.manage.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.article.bo.ArticleShortBo;
import com.kzm.blog.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 13:28 2020/4/14
 * @Version
 */
@RestController
@RequestMapping("/manage/article")
public class ArticleBackController {

    @Autowired
    private ArticleService articleService;


    @GetMapping("/list")
    //@RequiresPermissions("article:view")
    public Result getArticleList(ArticleShortBo articleShortBo) {
        return articleService.getAll(articleShortBo);
    }

    @GetMapping("/publish")
    //@RequiresPermissions("article:publish")
    public Result articlePublish(@RequestParam("id") Integer id) {
        boolean flag = articleService.update(new ArticleEntity().setPublish("1"),
                new QueryWrapper<ArticleEntity>().lambda().eq(ArticleEntity::getId, id));
        if (flag == false) {
            return Result.error(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @GetMapping("/recommend")
    //@RequiresPermissions("article:recommend")
    public Result articleRecommend(@RequestParam("id") Integer id, @RequestParam("type") Integer type) {
        boolean flag = articleService.update(new ArticleEntity().setRecommend(type), new QueryWrapper<ArticleEntity>().lambda().eq(ArticleEntity::getId, id));
        if (flag == false) {
            return Result.error(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @GetMapping("delete")
    //@RequiresPermissions("article:delete")
    public Result articleDelete(@RequestParam("id") Integer id) {
        return articleService.delteArticleByAdmin(id);

    }


}
