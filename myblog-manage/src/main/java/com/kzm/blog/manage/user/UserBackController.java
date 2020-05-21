package com.kzm.blog.manage.user;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.User.Bo.UserBackRegisterBo;
import com.kzm.blog.common.entity.User.Bo.UserBo;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.entity.role.bo.UserRoleBo;
import com.kzm.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 16:50 2020/4/13
 * @Version
 */
@RestController
@RequestMapping("/manage/user")
public class UserBackController {

    @Autowired
    private UserService userService;

    @GetMapping("frontView")
    //@RequiresPermissions("user:frontView")
    public Result userList(UserBo userBo) {
        return userService.getAllUser(userBo);
    }

    @GetMapping("backView")
    //@RequiresPermissions("user:backView")
    public Result backuserList(UserBo userBo) {
        return userService.getAllBackUser(userBo);
    }

    @GetMapping("online")
    public Result getUserOnline() {
        return null;
    }

    @GetMapping("forbid")
    //@RequiresPermissions("user:forbid")
    public Result forbid(@RequestParam("id") Integer id, @RequestParam("type") Integer type) {
        boolean b = userService.update(new UserEntity().setStatus(type),
                new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, id));
        if (b == false) {
            return Result.error(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @PostMapping("add")
    //@RequiresPermissions("user:add")
    public Result useradd(@RequestBody UserBackRegisterBo userBackRegisterBo) {
        return userService.addBackUser(userBackRegisterBo);

    }

    @PostMapping("edit")
    //@RequiresPermissions("user:edit")
    public Result userEdit(@RequestBody UserRoleBo  userRoleBo) {
       return userService.editBackUser(userRoleBo);

    }


}
