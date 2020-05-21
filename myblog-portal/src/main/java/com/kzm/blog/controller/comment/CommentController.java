package com.kzm.blog.controller.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.comment.CommentEntity;
import com.kzm.blog.common.entity.comment.bo.CommentBaseBo;
import com.kzm.blog.common.entity.comment.bo.CommentListBo;
import com.kzm.blog.common.entity.comment.bo.CommentSonBo;
import com.kzm.blog.common.entity.comment.bo.LikeBo;
import com.kzm.blog.service.article.ArticleService;
import com.kzm.blog.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 15:58 2020/3/30
 * @Version
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @PostMapping("postComment")
    @Log(module = "评论模块", operation = "提交评论")
    public Result postComment(@RequestBody CommentBaseBo commentBaseBo) {
        return commentService.saveComment(commentBaseBo);
    }

    @GetMapping("getAllComment")
    public Result getAllComments(CommentListBo commentListBo) {
        return  commentService.selectAllComment(commentListBo);
    }

    @GetMapping("deleteComment")
    public Result deleteComment(@RequestParam("id")Integer id) {
        return  commentService.deleteComments(id);
    }

    @PostMapping("postSonComment")
    @Log(module = "评论模块",operation = "回复子评论")
    public Result postSonComment(@RequestBody CommentSonBo commentSonBo){
        return commentService.saveSonComment(commentSonBo);
    }

    @GetMapping("/getCount")
    public Result getCount(@RequestParam("id") Integer id){
        ArticleEntity id1 = articleService.getById(id);
        return Result.success(id1.getCommentNum());
    }


    @PostMapping("/like")
    @Log(module = "评论模块",operation = "点赞")
    public Result like(@RequestBody LikeBo likeBo){
        return commentService.like(likeBo);
    }

}
