package com.kzm.blog.controller.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.entity.comment.CommentEntity;
import com.kzm.blog.common.entity.comment.bo.CommentBaseBo;
import com.kzm.blog.common.entity.comment.bo.CommentSonBo;
import com.kzm.blog.common.entity.comment.bo.LikeBo;
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

    @PostMapping("postComment")
    @Log(module = "评论模块", operation = "提交评论")
    public Result postComment(@RequestBody CommentBaseBo commentBaseBo) {
        return commentService.saveComment(commentBaseBo);
    }

    @GetMapping("getAllComment")
    @Log(module = "评论模块", operation = "获取文章下所有评论")
    public Result getAllComments(@RequestParam("id") Integer id) {
        return Result.success(commentService.selectAllComment(id));
    }

    @GetMapping("deleteComment")
    @Log(module = "评论模块", operation = "删除评论")
    public Result deleteComment() {
        return null;
    }
    @PostMapping("postSonComment")
    @Log(module = "评论模块",operation = "回复子评论")
    public Result postSonComment(@RequestBody CommentSonBo commentSonBo){
        return commentService.saveSonComment(commentSonBo);
    }

    @GetMapping("/getCount")
    @Log(module = "评论模块",operation = "获取评论总数")
    public Result getCount(@RequestParam("id") Integer id){
        int count = commentService.count(new QueryWrapper<CommentEntity>().lambda().eq(CommentEntity::getOwnerId, id));
        return Result.success(count);
    }

    @PostMapping("/like")
    @Log(module = "评论模块",operation = "点赞")
    public Result like(@RequestBody LikeBo likeBo){
        return commentService.like(likeBo);
    }

}
