package com.kzm.blog.manage.sys;

import com.kzm.blog.common.Result;
import com.kzm.blog.service.category.CategoryService;
import com.kzm.blog.service.comment.CommentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 14:06 2020/4/14
 * @Version
 */
@RestController
@RequestMapping("/manage/sys")
public class SysController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @GetMapping("category")
    //@RequiresPermissions("sys:category")
    public Result getSysCategory() {
        return categoryService.getCategorySys();
    }

    @GetMapping("comment")
    //@RequiresPermissions("sys:article")
    public Result getComments() {
        return commentService.getommentLine();
    }


}
