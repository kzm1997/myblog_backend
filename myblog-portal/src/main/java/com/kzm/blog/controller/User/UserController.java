package com.kzm.blog.controller.User;

import com.kzm.blog.common.Result;
import com.kzm.blog.common.annotation.Log;

import com.kzm.blog.common.entity.User.Bo.ExMaterialBo;
import com.kzm.blog.common.entity.User.Bo.PassWdBo;
import com.kzm.blog.common.entity.User.Bo.UserMaterialBo;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:28 2020/2/29
 * @Version
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/modifypasswd")
    @Log(module = "用户模块", operation = "更新密码")
    public Result changePassowrd(@RequestBody PassWdBo passWdBo){
        String userName = KblogUtils.getUserName();
        return   userService.changePassword(userName,passWdBo);
    }


    @PostMapping("/edit")
    @Log(module = "用户模块",operation = "更新资料")
    public Result edit(@RequestBody UserMaterialBo userMaterialBo){
       return userService.updateUserMaterial(userMaterialBo);
    }

    @PostMapping("/exMaterial")
    @Log(module = "用户模块",operation = "更新扩展资料")
    public Result editMaterial(@RequestBody ExMaterialBo exMaterialBo){
        return userService.updateExMaterial(exMaterialBo);
    }

    @PutMapping("/editEmail")
    @Log(module = "用户模块",operation = "修改邮箱")
    public Result updateEmail(@RequestParam String newEmail){
        return userService.updateEmail(newEmail);
    }

}
