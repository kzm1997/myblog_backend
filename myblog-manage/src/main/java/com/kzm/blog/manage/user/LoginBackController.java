package com.kzm.blog.manage.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.entity.User.Bo.UserLoginBo;
import com.kzm.blog.common.entity.User.vo.BackIndexVo;
import com.kzm.blog.common.entity.log.vo.Recent;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.service.article.ArticleService;
import com.kzm.blog.service.log.LoginLogService;
import com.kzm.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 9:52 2020/4/14
 * @Version
 */
@RequestMapping("/loginback")
@RestController
public class LoginBackController {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;


    /**
     * 获取系统首页访问量
     */
    @GetMapping("index")
    public Result index() {
        //获取总访问数
        Long totalVisisCount = loginLogService.findTotalVisitCount();
        //获取今日访问数
        Long todayVisitCount = loginLogService.findTodayVisitCount();
        //获取总文章数
        int articleCount = articleService.count();
        //获取近期系统的访问记录
        List<Recent> list = loginLogService.findLastSevenDaysVisitCount();
        //获取近日文章数
        List<Recent> recentList = articleService.getRecentArticle();
        BackIndexVo backIndexVo = new BackIndexVo();
        backIndexVo.setArticleCount(articleCount);
        backIndexVo.setTotalVisisCount(totalVisisCount);
        backIndexVo.setTodayVisitCount(todayVisitCount);
        backIndexVo.setTotalVisit(list);
        backIndexVo.setNewArticle(recentList);
        return Result.success(backIndexVo);
    }

    @PostMapping("/login")
    @Log(module = "后台首页", operation = "登录")
    public Result login(@RequestBody UserLoginBo userLoginBo) throws JsonProcessingException {
        return userService.login(userLoginBo);
    }


}
