package com.kzm.blog.manage.menu;

import com.kzm.blog.common.Result;
import com.kzm.blog.service.menu.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: kouzm
 * @Description: 菜单管理
 * @Date: Created in 13:26 2020/4/14
 * @Version
 */
@RestController
@RequestMapping("/manage/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    @RequiresPermissions("menu:view")
    public Result getMenu() {
        return menuService.getMenu();
    }
}
