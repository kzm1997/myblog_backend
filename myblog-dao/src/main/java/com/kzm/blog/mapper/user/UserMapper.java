package com.kzm.blog.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.entity.User.vo.UserRecommendVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 通过用户名获取用户id
     * @param userName
     * @return
     */
    Integer getIdByAccount(@Param("userName") String userName);

    /**
     * 获取推介用户
     * @return
     */
    List<UserRecommendVo> selectUserRecommend();
}
