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
import javax.validation.Valid;



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
    public Result changePassowrd(@RequestBody @Valid PassWdBo passWdBo) {
        String userName = KblogUtils.getUserName();
        return userService.changePassword(userName, passWdBo);
    }


    @PostMapping("/edit")
    @Log(module = "用户模块", operation = "更新资料")
    public Result edit(@RequestBody UserMaterialBo userMaterialBo) {
        return userService.updateUserMaterial(userMaterialBo);
    }

    @PostMapping("/exMaterial")
    @Log(module = "用户模块", operation = "更新扩展资料")
    public Result editMaterial(@RequestBody ExMaterialBo exMaterialBo) {
        return userService.updateExMaterial(exMaterialBo);
    }

    @PutMapping("/editEmail")
    @Log(module = "用户模块", operation = "修改邮箱")
    public Result updateEmail(@RequestParam String newEmail) {
        return userService.updateEmail(newEmail);
    }

    @GetMapping("/referral")
    @Log(module = "用户模块", operation = "推荐作者")
    public Result referralUser() {
        return userService.getFerralUser();
    }

    @GetMapping("/getUser")
    @Log(module = "用户模块", operation = "获取用户数据")
    public Result getUser() {
        return userService.getUser();
    }

    @GetMapping("/checkForm")
    @Log(module = "用户模块",operation = "用户信息唯一性校验")
    public Result checkForm(@RequestParam String key,@RequestParam String value){
        return  userService.checkForm(key,value);
    }

    @GetMapping("/like")
    @Log(module = "用户模块",operation = "关注")
    public Result userlike(@RequestParam("userId") Integer userId,@RequestParam("type") Integer type){
        return userService.userLike(userId,type);
    }

}
