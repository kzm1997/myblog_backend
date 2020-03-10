package com.kzm.blog.controller.User;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.User.Bo.UserLoginBo;
import com.kzm.blog.common.entity.User.Bo.UserRegisterBo;

import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 22:46 2020/2/28
 * @Version
 */
@RestController
@RequestMapping("/userlogin")
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping("register")
    @Log(module = "注册", operation = "注册")
    public void register(@RequestBody UserRegisterBo userRegisterBo) throws Exception {
        this.userService.register(userRegisterBo);
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginBo userLoginBo) throws Exception {
        return userService.login(userLoginBo);
    }

    @GetMapping("/checkParam")
    public Result checkParam(@RequestParam("value") @NotBlank String value,
                             @RequestParam("type") @NotBlank String type)throws Exception{
        if (userService.checkParam(value, type)) {
            throw new KBlogException(ResultCode.PARAM_ONLY_HAS);
        }
        return Result.success();
    }
}
