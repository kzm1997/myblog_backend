package com.kzm.blog.manage.comment;

import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.article.bo.ArticleShortBo;
import com.kzm.blog.common.entity.comment.bo.CommentListBo;
import com.kzm.blog.service.article.ArticleService;
import com.kzm.blog.service.comment.CommentService;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 13:51 2020/4/14
 * @Version
 */
@RestController
@RequestMapping("/manage/comment")
public class CommentBackController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @GetMapping("list")
    //@RequiresPermissions("comment:list")
    public Result getList(ArticleShortBo articleShortBo){
      return articleService.getArticleByCategory(articleShortBo);
    }

    @GetMapping("view")
    //@RequiresPermissions("comment:view")
    public Result getComment(CommentListBo commentListBo){
        return commentService.selectAllComment(commentListBo);
    }

    @GetMapping("/delete")
    //@RequiresPermissions("comment:delete")
    public Result deleteComment(@RequestParam("id")Integer id){
        return commentService.deleteComments(id);
    }



}
