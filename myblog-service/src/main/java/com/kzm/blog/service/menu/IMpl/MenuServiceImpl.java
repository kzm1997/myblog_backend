package com.kzm.blog.service.menu.IMpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.common.Tree;
import com.kzm.blog.common.entity.menu.MenuEntity;
import com.kzm.blog.mapper.menu.MenuMapper;
import com.kzm.blog.service.menu.MenuService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 12:25 2020/5/5
 * @Version
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Result getMenu() {
        List<MenuEntity> menus = menuMapper.selectList(new QueryWrapper<>());
        List<Tree<MenuEntity>> trees = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
         //buildTree();
        return null;
    }

    private void buildTree(List<Tree<MenuEntity>>trees,List<MenuEntity>menus,List<Integer>ids){

    }
}
