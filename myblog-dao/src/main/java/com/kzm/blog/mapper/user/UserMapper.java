package com.kzm.blog.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.kzm.blog.common.entity.User.Bo.UserBo;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.entity.User.vo.UserFerralVo;
import com.kzm.blog.common.entity.User.vo.UserLikeVo;
import com.kzm.blog.common.entity.User.vo.UserListVo;
import com.kzm.blog.common.entity.User.vo.UserRecommendVo;
import com.kzm.blog.common.entity.article.vo.ArticleRecentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    /**
     * 查询用户的角色信息
     * @param id
     * @return
     */
    Set<String> getUserRoles(@Param("userId") Integer id);

    /**
     * 查询用户的权限信息
     * @param id
     * @return
     */
    Set<String> getUserPermissions(@Param("userId") Integer id);

    IPage<UserListVo> getAllUser(@Param("page")Page<UserListVo> page, @Param("userBo") UserBo userBo);

    int getArtcileCount(@Param("id") Integer id);

    IPage<UserListVo> getAllBackUser(@Param("page")Page<UserListVo> page, @Param("userBo") UserBo userBo);

    /**
     * 添加后台用户角色关系
     * @param id
     * @param roleId
     * @return
     */
    int addBackUserRole(@Param("userId") Integer id, @Param("roleId") Integer roleId);

    int updateUserRole(@Param("userId") Integer id,@Param("roleId") Integer roleId);

    List<ArticleRecentVo> getArticleRecent(@Param("id") Integer id);

    List<UserLikeVo> selectUserLike(@Param("fromId") Integer id);

    /**
     * 查询某个人的粉丝
     * @param id
     * @return
     */
    Integer selectLikeNumById(@Param("userId") Integer id);


    List<ArticleRecentVo> selectMyNewCommentArticle(@Param("userId") Integer id);
}
