package com.kzm.blog.controller.User;


import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.User.Bo.UserLoginBo;
import com.kzm.blog.common.entity.User.Bo.UserRegisterBo;
import com.kzm.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @PostMapping("/login")
    @Log(module = "登录", operation = "登录")
    public Result login(@RequestBody UserLoginBo userLoginBo, HttpServletRequest httpServletRequest) {
        return userService.login(userLoginBo,httpServletRequest);
    }

    @PostMapping("/register")
    @Log(module = "注册", operation = "注册")
    public Result register(@RequestBody @Validated UserRegisterBo userRegisterBo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> paramError = new LinkedHashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                paramError.put("paramError", fieldError.getDefaultMessage());
                break;
            }
            return Result.error(ResultCode.PARAM_IS_INVALID);
        }

        return userService.register(userRegisterBo);
    }
}
