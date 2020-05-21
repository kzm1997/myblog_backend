package com.kzm.blog.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.entity.role.RoleEntity;
import com.kzm.blog.mapper.role.RoleMapper;
import com.kzm.blog.service.role.RoleService;
import org.springframework.stereotype.Service;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 12:48 2020/5/3
 * @Version
 */
@Service
public class RoleServiceImpl  extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
}
