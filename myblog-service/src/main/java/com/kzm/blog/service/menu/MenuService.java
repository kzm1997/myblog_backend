package com.kzm.blog.service.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.menu.MenuEntity;

public interface MenuService extends IService<MenuEntity> {

    /**
     * 获取菜单
     * @return
     */
    Result getMenu();
}
