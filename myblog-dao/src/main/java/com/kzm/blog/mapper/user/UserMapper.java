package com.kzm.blog.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kzm.blog.common.entity.User.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
