package com.kzm.blog.service.user.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kzm.blog.common.base.BaseEntity;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.User.ActiveUser;
import com.kzm.blog.common.entity.User.Bo.*;
import com.kzm.blog.common.entity.User.TimeComparator;
import com.kzm.blog.common.entity.User.vo.*;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.article.vo.ArticleRecentVo;
import com.kzm.blog.common.entity.article.vo.ArticleShortVo;
import com.kzm.blog.common.entity.comment.CommentEntity;
import com.kzm.blog.common.entity.role.bo.UserRoleBo;
import com.kzm.blog.common.utils.*;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constants.Base;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.common.exception.RedisException;
import com.kzm.blog.common.properties.KBlogProperties;
import com.kzm.blog.mapper.article.ArticleMapper;
import com.kzm.blog.mapper.comment.CommentMapper;
import com.kzm.blog.mapper.user.UserMapper;
import com.kzm.blog.service.cache.CacheService;
import com.kzm.blog.service.redis.RedisService;
import com.kzm.blog.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private KBlogProperties blogProperties;

    @Autowired
    private ObjectMapper objectMapper;

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
        userEntity.setNickname("");
        try {
            userMapper.insert(userEntity);
        } catch (Exception e) {
            throw new KBlogException(ResultCode.DATA_INSERT_ERR);
        }

    }

    /**
     * 待考量
     * 将用户相关信息保存到Redis中
     *
     * @param userEntity
     */
    private void loadUserToRedis(UserEntity userEntity) throws RedisException, JsonProcessingException {
        cacheService.saveUser(userEntity.getAccount());
    }

    @Override
    public Result login(UserLoginBo userLoginBo) throws KBlogException, RedisException, JsonProcessingException {
        UserEntity userEntity = new UserEntity();
        UserEntity entity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, userLoginBo.getAccount()));
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
        //生成token,用户密码做token签名密钥,DES加密采用redis前缀
        String token = KblogUtils.encryptToken(JWTUtil.sign(entity.getAccount(), entity.getPassword()));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(blogProperties.getJwtTimeOut());
        String expireTimeStr = TimeUtils.LocalDateTimeToString(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        //将token存到redis中
        this.saveTokenToRedis(userEntity, jwtToken);
        //返回token
        Set<String> roles = userMapper.getUserRoles(entity.getId());
        if (roles.size() > 0) {
            //封装用户权限信息
            UserBackInfoVo userBackInfoVo = new UserBackInfoVo();
            UserInfoVo userInfoVo = new UserInfoVo();
            UserEntityVo userEntityVo = new UserEntityVo();
            BeanUtils.copyProperties(entity, userEntityVo);
            userInfoVo.setToken(jwtToken.getToken());
            userInfoVo.setExpireTime(jwtToken.getExipreAt());
            userInfoVo.setUserEntityVo(userEntityVo);
            userBackInfoVo.setUserInfoVo(userInfoVo);
            Set<String> permissions = userMapper.getUserPermissions(entity.getId());
            userBackInfoVo.setPermissions(permissions);
            userBackInfoVo.setRoles(roles);
            return Result.success(userBackInfoVo);
        } else {
            UserInfoVo userInfoVo = new UserInfoVo();
            userInfoVo.setExpireTime(jwtToken.getExipreAt());
            userInfoVo.setToken(jwtToken.getToken());
            return Result.success(userInfoVo);
        }
    }

    private String saveTokenToRedis(UserEntity userEntity, JWTToken jwtToken) throws RedisException, JsonProcessingException {
        HttpServletRequest httpServeltRequest = HttpContextUtils.getHttpServeltRequest();
        String ip = IPUtils.getIpaddr(httpServeltRequest);
        //构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setAccount(userEntity.getAccount());
        activeUser.setId(ip);
        activeUser.setToken(jwtToken.getToken());
        activeUser.setLoginAddress(AddressUtil.getCityInfo(ip));
        //zset存储d登录用户,score为过期时间戳
        redisService.zadd(Base.cache.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(jwtToken.getExipreAt())
                , objectMapper.writeValueAsString(activeUser));
        //将token存到redis
        redisService.set(Base.TOKEN_CACHE_PREFIX + StringPool.DOT + jwtToken.getToken(),
                jwtToken.getToken(), blogProperties.getJwtTimeOut() * 1000);
        return activeUser.getId();
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
            case "nickname": {
                Integer count = userMapper.selectCount(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getNickname, value));
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
        userTest.setPassword(passWdBo.getNewPassWd());
        PasswordHelper.encryptPassword(userTest);
        BeanUtil.copyProperties(userTest, userEntity, CopyOptions.create().setIgnoreNullValue(true));
        userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(BaseEntity::getId, userEntity.getId()));
        return Result.success();
    }

    @Override
    public Result updateExMaterial(ExMaterialBo exMaterialBo) {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = new UserEntity();
        BeanUtil.copyProperties(exMaterialBo, userEntity);
        int i = userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        if (i == 0) {
            throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @Override
    public Result updateUserMaterial(UserMaterialBo userMaterialBo) {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = new UserEntity();
        BeanUtil.copyProperties(userMaterialBo, userEntity);
        int i = userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        if (i == 0) {
            throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @Override
    public Result updateEmail(String newEmail) {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(newEmail);
        int i = userMapper.update(userEntity, new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        if (i == 0) {
            throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @Override
    public Result logout() {
        //todo 退出登录
        return null;
    }

    @Override
    public Result getFerralUser() {
        List<Integer> except = new ArrayList<>();
        String userName = KblogUtils.getUserName();
        if (StringUtils.isNotBlank(userName)) {
            Integer id = userMapper.getIdByAccount(userName);
            List<Integer> likeUser = userMapper.selectUnLikeUser(id);
            likeUser.add(id);
            except = likeUser;
        }
        Integer count = userMapper.selectCount(new QueryWrapper<>());
        ArrayList<Integer> arrayList = getRandomNonRepeatingIntegers(5, 1, count, except);
        List<UserFerralVo> list = userMapper.getFerralUser(arrayList);
        List<Integer> collect = list.stream().map(UserFerralVo::getId).collect(Collectors.toList());
        List<UserFerralVo> userFerralVos = articleMapper.getWordCount(collect);
        for (UserFerralVo userFerralVo : list) {
            boolean flag = true;
            for (UserFerralVo ferralVo : userFerralVos) {
                if (ferralVo.getId() == userFerralVo.getId()) {
                    userFerralVo.setTotalWord(ferralVo.getTotalWord());
                    flag = false;
                    break;
                }
            }
            if (flag) {
                userFerralVo.setTotalWord(0);
            }
        }
        return Result.success(list);
    }

    private Integer getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min, int max, List<Integer> except) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        while (numbers.size() < size) {
            int random = getRandomInt(min, max);
            if (except.size() != 0 && except.contains(random)) {
                continue;
            }
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        return numbers;
    }

    @Override
    public Result getUser() {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        UserBaseInfoVo userBaseInfoVo = new UserBaseInfoVo();
        BeanUtil.copyProperties(userEntity, userBaseInfoVo);
        return Result.success(userBaseInfoVo);
    }


    @Override
    public Result checkForm(String key, String value) {
        if (this.checkParam(value, key)) {
            return Result.error(ResultCode.PARAM_ONLY_HAS);
        }
        return Result.success();
    }

    @Override
    public Result userLike(Integer userId, Integer type) {
        String userName = KblogUtils.getUserName();
        Integer id = userMapper.getIdByAccount(userName);
        if (type == 1) {
            try {
                int count = userMapper.selectLikeCount(id, userId);
                if (count == 0) {
                    int flag = userMapper.insertUserLikeRelation(id, userId);
                } else {
                    int flag = userMapper.updateUserLikeRelation(id, userId, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
            }
        } else if (type == 0) {
            try {
                userMapper.updateUserLikeRelation(id, userId, 0);
            } catch (Exception e) {
                e.printStackTrace();
                throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
            }
        }
        return Result.success();
    }

    @Override
    public Set<String> getUserRoles(String account) {

        return null;
    }

    @Override
    public Result getAllUser(UserBo userBo) {
        Page<UserListVo> page = new Page<>(userBo.getPageNum(), userBo.getPageSize());
        page = (Page<UserListVo>) userMapper.getAllUser(page, userBo);
        List<UserListVo> records = page.getRecords();
        for (UserListVo record : records) {
            int artcileCount = userMapper.getArtcileCount(record.getId());
            record.setArticles(artcileCount);
        }
        page.setRecords(records);
        MyPage<UserListVo> myPage = new MyPage<UserListVo>().getMyPage(page);
        return Result.success(myPage);

    }

    @Override
    public Result getAllBackUser(UserBo userBo) {
        Page<UserListVo> page = new Page<>(userBo.getPageNum(), userBo.getPageSize());
        page = (Page<UserListVo>) userMapper.getAllBackUser(page, userBo);
        MyPage<UserListVo> myPage = new MyPage<UserListVo>().getMyPage(page);
        return Result.success(myPage);
    }


    @Override
    @Transactional
    public Result addBackUser(UserBackRegisterBo userBackRegisterBo) {
        UserEntity userEntity = new UserEntity();
        BeanUtil.copyProperties(userBackRegisterBo, userEntity);
        boolean account = this.checkParam(userEntity.getAccount(), "account");
        if (account) {
            throw new KBlogException(ResultCode.USER_HAS_EXISTED);
        }
        boolean email = this.checkParam(userEntity.getEmail(), "email");
        if (email) {
            throw new KBlogException(ResultCode.EMAIL_HAS_EXISTED);
        }
        boolean nickname = this.checkParam(userEntity.getNickname(), "nickname");
        if (nickname) {
            throw new KBlogException(ResultCode.USER_NICKNAME_ERROR);
        }
        PasswordHelper.encryptPassword(userEntity);
        int i = userMapper.insert(userEntity);
        if (i == 0) {
            return Result.error(ResultCode.DATA_INSERT_ERR);
        }
        int b = userMapper.addBackUserRole(userEntity.getId(), userBackRegisterBo.getRoleId());
        if (b == 0) {
            return Result.error(ResultCode.DATA_INSERT_ERR);
        }
        return Result.success();
    }

    @Override
    public Result editBackUser(UserRoleBo userRoleBo) {
        int b = userMapper.updateUserRole(userRoleBo.getUserId(), userRoleBo.getRoleId());
        if (b == 0) {
            return Result.error(ResultCode.DATA_UPDATE_ERR);
        }
        return Result.success();
    }

    @Override
    public Result getUserArticles() {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, userName));
        List<ArticleRecentVo> myArticleRecent = userMapper.getArticleRecent(userEntity.getId());
        myArticleRecent.forEach(articleRecentVo -> articleRecentVo.setType("article"));
        return Result.success(myArticleRecent);
    }

    @Override
    public Result getDynamic() {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, userName));
        //获取文章
        List<ArticleRecentVo> myArticleRecent = userMapper.getArticleRecent(userEntity.getId());
        myArticleRecent.forEach(articleRecentVo -> articleRecentVo.setType("myArticle"));
        //获取评论
        List<CommentEntity> commentEntities = commentMapper.selectList(new QueryWrapper<CommentEntity>().lambda()
                .eq(CommentEntity::getFromId, userEntity.getId()).gt(CommentEntity::getCreateTime, LocalDate.now().plusMonths(-1)));
        List<ArticleRecentVo> collect = commentEntities.stream().map(commentEntity -> {
            ArticleRecentVo articleRecentVo = new ArticleRecentVo();
            articleRecentVo.setComment(commentEntity.getContent());
            articleRecentVo.setUpdateTime(commentEntity.getCreateTime());
            articleRecentVo.setId(commentEntity.getId());
            ArticleEntity articleEntity = articleMapper.selectById(commentEntity.getOwnerId());
            articleRecentVo.setReadNum(articleEntity.getReadNum());
            articleRecentVo.setCommentNum(articleEntity.getCommentNum());
            articleRecentVo.setSummary(articleEntity.getDescription());
            articleRecentVo.setTitle(articleEntity.getTitle());
            articleRecentVo.setId(articleEntity.getId());
            articleRecentVo.setType("comment");
            return articleRecentVo;
        }).collect(Collectors.toList());
        //查询关注用户
        List<UserLikeVo> userLikeVos = userMapper.selectUserLike(userEntity.getId());
        for (UserLikeVo userLikeVo : userLikeVos) {
            userLikeVo.setLikeFlag(true);
            userLikeVo.setTotalWord(articleMapper.selectArticleWord(userLikeVo.getToUserId()));
            userLikeVo.setLikeNum(userMapper.selectLikeNumById(userLikeVo.getToUserId()));
            userLikeVo.setType("like");
        }
        ArrayList<TimeComparator> list = new ArrayList<>();
        list.addAll(myArticleRecent);
        list.addAll(collect);
        list.addAll(userLikeVos);
        TimeComparator[] timeComparators=new TimeComparator[]{};
        TimeComparator[] comparators = list.toArray(timeComparators);
        Arrays.sort(comparators);
        return Result.success(comparators);
    }

    @Override
    public Result getMyArticleComments() {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, userName));
        //获取我的最新评论文章
        List<ArticleRecentVo> articleRecentVos = userMapper.selectMyNewCommentArticle(userEntity.getId());
        articleRecentVos.forEach(articleRecentVo -> articleRecentVo.setType("article"));
        return Result.success(articleRecentVos);
    }

    @Override
    public Result getMyHot() {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, userName));
        List<ArticleRecentVo> articleRecentVos = articleMapper.selectHotRecent(userEntity.getId());
        articleRecentVos.forEach(articleRecentVo -> articleRecentVo.setType("article"));
        return Result.success(articleRecentVos);
    }

    @Override
    public Result getTimeLine() {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, userName));
       List<ArticleShortVo> list=articleMapper.getTimeLine(userEntity.getId());
        return Result.success(list);
    }
}

