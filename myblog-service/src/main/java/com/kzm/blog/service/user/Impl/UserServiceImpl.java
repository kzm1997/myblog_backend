package com.kzm.blog.service.user.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kzm.blog.common.base.BaseEntity;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.User.Bo.*;
import com.kzm.blog.common.entity.User.vo.UserEntityVo;
import com.kzm.blog.common.utils.JWTToken;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constants.Base;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.entity.User.vo.UserInfoVo;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.common.exception.RedisException;
import com.kzm.blog.common.properties.KBlogProperties;
import com.kzm.blog.common.utils.JWTUtil;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.common.utils.PasswordHelper;
import com.kzm.blog.common.utils.TimeUtils;
import com.kzm.blog.mapper.user.UserMapper;
import com.kzm.blog.service.cache.CacheService;
import com.kzm.blog.service.redis.RedisService;
import com.kzm.blog.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:50 2020/2/28
 * @Version
 */
@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private KBlogProperties blogProperties;

    @Autowired
    private RedisService redisService;

    @Override
    public void register(UserRegisterBo userRegisterBo) throws KBlogException, RedisException, JsonProcessingException {
        //查询账号是否存在
        if (this.checkParam(userRegisterBo.getAccount(), "account")) {
            throw new KBlogException(ResultCode.USER_HAS_EXISTED);
        }
        //查询email是否重复
        if (this.checkParam(userRegisterBo.getEmail(), "email")) {
            throw new KBlogException(ResultCode.EMAIL_HAS_EXISTED);
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userRegisterBo, userEntity);
        PasswordHelper.encryptPassword(userEntity);
        userEntity.setAvatar(Base.user.DEFAULT_AVATAR);
        userEntity.setStatus(Base.user.STATUS_VALID);
        userEntity.setSex(Base.user.SEX_UNKNOW);

        userEntity.setEmail(userRegisterBo.getEmail());
        try {
            userMapper.insert(userEntity);
        } catch (Exception e) {
            throw new KBlogException(ResultCode.DATA_INSERT_ERR);
        }
        //todo 用户的默认角色和权限配置
        this.loadUserToRedis(userEntity);
    }

    /**
     * 将用户相关信息保存到Redis中
     *
     * @param userEntity
     */
    private void loadUserToRedis(UserEntity userEntity) throws RedisException, JsonProcessingException {
        cacheService.saveUser(userEntity.getAccount());
    }

    @Override
    public Result login(UserLoginBo userLoginBo) throws KBlogException, RedisException {
        UserEntity userEntity = new UserEntity();
        UserEntity entity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userLoginBo.getAccount()));
        if (entity == null) {
            throw new KBlogException(ResultCode.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(userLoginBo, userEntity);
        userEntity.setSalt(entity.getSalt());
        String password = PasswordHelper.authenticatPassword(userEntity);

        if (!StringUtils.equals(entity.getPassword(), password)) {
            throw new KBlogException(ResultCode.USER_LOGIN_ERROR);
        }
        if (Base.user.STATUS_LOCK.equals(entity.getStatus())) {
            throw new KBlogException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        //todo  更新用户登录时间

        //生成token,用户密码做token签名密钥,DES加密采用redis前缀
        String token = KblogUtils.encryptToken(JWTUtil.sign(entity.getAccount(), entity.getPassword()));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(blogProperties.getJwtTimeOut());
        String expireTimeStr = TimeUtils.LocalDateTimeToString(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        //将token存到redis中
        this.saveTokenToRedis(entity, jwtToken);
        //返回用户数据 //todo 待封装
        UserInfoVo userInfoVo = new UserInfoVo();
        UserEntityVo userEntityVo = new UserEntityVo();
        BeanUtils.copyProperties(entity, userEntityVo);
        userInfoVo.setUserEntityVo(userEntityVo);
        userInfoVo.setToken(jwtToken.getToken());
        return Result.success(userInfoVo);
    }

    private String saveTokenToRedis(UserEntity userEntity, JWTToken jwtToken) throws RedisException {
        return redisService.set(Base.TOKEN_CACHE_PREFIX + StringPool.DOT + jwtToken.getToken(), jwtToken.getToken(), blogProperties.getJwtTimeOut() * 1000);
    }

    @Override
    public boolean checkParam(String value, String type) {
        switch (type.toLowerCase()) {
            case "email": {
                Integer count = userMapper.selectCount(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getEmail, value));
                if (count > 0)
                    return true;
            }
            case "account": {
                Integer count = userMapper.selectCount(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, value));
                if (count > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Result changePassword(String userName, PassWdBo passWdBo) {
        if (!StringUtils.equals(passWdBo.getNewPassWd(), passWdBo.getConfirmWd())) {
            throw new KBlogException(ResultCode.PARAM_NOT_SAME_PASSWORD);
        }
        //检查密码是否错误
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        UserEntity userTest = new UserEntity();
        userTest.setAccount(userName).setPassword(passWdBo.getPassword()).setSalt(userEntity.getSalt());
        String passwordTest = PasswordHelper.authenticatPassword(userTest);
        if (!StringUtils.equals(passwordTest, userEntity.getPassword())) {
            throw new KBlogException(ResultCode.USeR_PASSWD_ERROR);
        }

        //修改密码
        PasswordHelper.encryptPassword(userTest);
        BeanUtil.copyProperties(userTest, userEntity, CopyOptions.create().setIgnoreNullValue(true));
        userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(BaseEntity::getId, userEntity.getId()));
        return Result.success();
    }

    @Override
    public Result updateExMaterial(ExMaterialBo exMaterialBo) {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity=new UserEntity();
        BeanUtil.copyProperties(exMaterialBo,userEntity);
        int i = userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        if (i==0){
            throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @Override
    public Result updateUserMaterial(UserMaterialBo userMaterialBo) {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity=new UserEntity();
        BeanUtil.copyProperties(userMaterialBo,userEntity);
        int i = userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        if (i==0){
            throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @Override
    public Result updateEmail(String newEmail) {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail(newEmail);
        int i = userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        if (i==0){
            throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

}

