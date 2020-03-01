package com.kzm.blog.controller.User;

import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.constants.Base;
import com.kzm.blog.common.entity.User.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:28 2020/2/29
 * @Version
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/currentUser")
    @Log(module = "用户模块",operation = "获取当前用户")
    public Result getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute(Base.CURRENT_USER);
        return Result.success(user);
    }
}
