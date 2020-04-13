package com.kzm.blog.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.entity.User.vo.UserFerralVo;
import com.kzm.blog.common.entity.User.vo.UserRecommendVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
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

    List<UserFerralVo> getFerralUser(@Param("ids") ArrayList<Integer> arrayList);

    List<Integer> selectUnLikeUser(@Param("userId") Integer id);



    int selectLikeCount(@Param("fromId") Integer id, @Param("toId") Integer userId);

    /**
     * 插入用户关注信息
     * @param id
     * @param userId
     * @return
     */
    int insertUserLikeRelation(@Param("fromId") Integer id, @Param("toId")Integer userId);

    /**
     * 更新用户关注信息
     * @param id
     * @param userId
     * @param status
     * @return
     */
    int updateUserLikeRelation(@Param("fromId")Integer id, @Param("toId")Integer userId,@Param("status") Integer status);


    /**
     * 查询用户是否关注某用户
     * @param id
     * @param userId
     * @return
     */
    int selectIsLikeCount(@Param("fromId") Integer id, @Param("toId") Integer userId);
}
