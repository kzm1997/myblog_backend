package com.kzm.blog.auth.Realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.utils.JWTToken;
import com.kzm.blog.common.constants.Base;
import com.kzm.blog.common.exception.RedisException;
import com.kzm.blog.common.utils.JWTUtil;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.service.redis.RedisService;
import com.kzm.blog.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: kouzm
 * @Description: 认证和授权
 * @Date: Created in 11:13 2020/3/6
 * @Version
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private RedisService redisService;


    @Autowired
    private UserService userService;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String account = JWTUtil.getAccount(principalCollection.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();

        userService.getUserRoles(account);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 用户认证token
     * @return
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //这里的token是从JWTFilter的 excuteLogin 方法传递过来的,已经经过了解密
        String token = (String) authenticationToken.getCredentials();


        //从redis里获取这个token
        String encryptToken = KblogUtils.encryptToken(token);
        String encryptTokenInRedis = null;
        try {
            encryptTokenInRedis = redisService.get(Base.TOKEN_CACHE_PREFIX + StringPool.DOT+encryptToken);
        } catch (RedisException e) {
            e.printStackTrace();
        }
        //如果找不到,说明已经失效
        if (StringUtils.isBlank(encryptTokenInRedis)) {
            throw new AuthenticationException("token已经过期");
        }
        String account = JWTUtil.getAccount(token);
        if (StringUtils.isBlank(account)){
            throw new AuthenticationException("token校验不通过");
        }
        UserEntity userEntity = userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, account));
        if (userEntity==null){
            throw new AuthenticationException("token校验不通过");
        }
        if (!JWTUtil.verify(token,account,userEntity.getPassword())){
            throw new AuthenticationException("token校验不通过");
        }
        return new SimpleAuthenticationInfo(token,token,"kblog_realm");
    }
}
