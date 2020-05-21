package com.kzm.blog.manage.role;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.base.PageBo;
import com.kzm.blog.common.entity.role.RoleEntity;
import com.kzm.blog.common.utils.MyPage;
import com.kzm.blog.service.role.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: kouzm
 * @Description: 角色管理
 * @Date: Created in 13:25 2020/4/14
 * @Version
 */
@RestController
@RequestMapping("/manage/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("select")
    public Result getselect() {
        List<RoleEntity> list = roleService.list();
        return Result.success(list);
    }

    @GetMapping("list")
    //@RequiresPermissions("role:list")
    public Result getList(PageBo pageBo) {
        Page<RoleEntity> page = (Page<RoleEntity>) roleService.page(new Page<>(pageBo.getPageNum(), pageBo.getPageSize())
                , new QueryWrapper<>());
        MyPage<RoleEntity> myPage = new MyPage<RoleEntity>().getMyPage(page);
        return Result.success(myPage);
    }



}
