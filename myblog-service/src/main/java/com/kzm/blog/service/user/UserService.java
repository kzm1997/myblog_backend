package com.kzm.blog.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.User.Bo.*;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.common.exception.RedisException;


public interface UserService extends IService<UserEntity> {

    /**
     * 注册
     * @param userRegisterBo
     * @throws KBlogException
     * @throws RedisException
     * @throws JsonProcessingException
     */
    void register(UserRegisterBo userRegisterBo) throws KBlogException, RedisException, JsonProcessingException;

    /**
     * 登录
     * @param userLoginBo
     * @return
     * @throws KBlogException
     * @throws RedisException
     */
    Result login(UserLoginBo userLoginBo) throws KBlogException, RedisException;

    /**
     * 表单参数唯一性检查
     * @param value
     * @param type
     * @return
     */
    boolean checkParam(String value, String type);

    /**
     * 修改密码
     * @param userName
     * @param passWdBo
     * @return
     */
    Result changePassword(String userName, PassWdBo passWdBo);

    /**
     * 更新用户扩展资料
     * @param exMaterialBo
     * @return
     */
    Result updateExMaterial(ExMaterialBo exMaterialBo);

    /**
     * 更新用户基本资料
     * @param userMaterialBo
     * @return
     */
    Result updateUserMaterial(UserMaterialBo userMaterialBo);

    /**
     * 更新邮箱
     * @param newEmail
     * @return
     */
    Result updateEmail(String newEmail);
}
