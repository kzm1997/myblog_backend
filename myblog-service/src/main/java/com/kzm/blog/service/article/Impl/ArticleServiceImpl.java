package com.kzm.blog.service.article.Impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.entity.User.vo.UserAuthorVo;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.article.bo.ArticleShortBo;
import com.kzm.blog.common.entity.article.bo.ArticleUploadBo;
import com.kzm.blog.common.entity.article.vo.*;
import com.kzm.blog.common.entity.category.CategoryEntity;
import com.kzm.blog.common.entity.category.vo.CategoryNameVo;
import com.kzm.blog.common.entity.category.vo.CategoryShortVo;
import com.kzm.blog.common.entity.comment.CommentEntity;
import com.kzm.blog.common.entity.log.vo.Recent;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.common.utils.MyPage;
import com.kzm.blog.mapper.article.ArticleMapper;
import com.kzm.blog.mapper.category.CategoryMapper;
import com.kzm.blog.mapper.comment.CommentMapper;
import com.kzm.blog.mapper.user.UserMapper;
import com.kzm.blog.service.article.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 16:08 2020/3/1
 * @Version
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 通过分类id获取文章,0代表全部,-1代表热点文章
     *
     * @param articleShortBo
     * @return
     */
    @Override
    public Result getArticleByCategory(ArticleShortBo articleShortBo) {
        Page<ArticleShortVo> page = new Page<>(articleShortBo.getPageNum(), articleShortBo.getPageSize());
        page = (Page<ArticleShortVo>) articleMapper.selectShortByTag(page, articleShortBo);
        //查询文章的分类
        List<ArticleShortVo> articleShortVos = page.getRecords();
        for (ArticleShortVo articleShortVo : articleShortVos) {
            List<CategoryNameVo> categoryNameVos = categoryMapper.selectByArticleId(articleShortVo.getId());
            List<CategoryShortVo> categoryShortVos = categoryNameVos.stream().map(nameVo -> {
                CategoryShortVo temp = new CategoryShortVo();
                BeanUtil.copyProperties(nameVo, temp);
                return temp;
            }).collect(Collectors.toList());
            articleShortVo.setCategorys(categoryShortVos);
        }
        page.setRecords(articleShortVos);
        MyPage<ArticleShortVo> myPage = new MyPage<ArticleShortVo>().getMyPage(page);
        return Result.success(myPage);
    }

    @Override
    public Result getArticle(Integer id) {
        ArticleEntity articleEntity = articleMapper.selectById(id);
        ArticleViewVo articleViewVo = new ArticleViewVo();
        BeanUtil.copyProperties(articleEntity, articleViewVo);
        //获取分类
        List<CategoryNameVo> categoryNameVos = categoryMapper.selectByArticleId(id);
        articleViewVo.setTags(categoryNameVos);
        //获取用户信息
        UserEntity userEntity = userMapper.selectById(articleEntity.getAuthorId());
        UserAuthorVo authorVo = new UserAuthorVo();
        BeanUtil.copyProperties(userEntity, authorVo);
        //获取关注信息
        String userName = KblogUtils.getUserName();
        if (StringUtils.isNotBlank(userName)){
            Integer userid = userMapper.getIdByAccount(userName);
            int i = userMapper.selectIsLikeCount(userid, userEntity.getId());
            if (i!=0){
                authorVo.setLike(true);
            }
        }
        articleViewVo.setAuthorVo(authorVo);
        return Result.success(articleViewVo);
    }

    @Override
    @Transactional
    public Result delteArticle(Integer id) {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        ArticleEntity articleEntity = articleMapper.selectById(id);
        if (!BeanUtil.isEmpty(userEntity) && !BeanUtil.isEmpty(articleEntity)) {
            if (userEntity.getId() == articleEntity.getAuthorId()) {
                try {
                    //删除文章
                    articleMapper.deleteById(id);
                    //删除关系
                    categoryMapper.deleteArticle(id);
                    //删除评论
                    List<CommentEntity> commentEntities = commentMapper.selectList(new QueryWrapper<CommentEntity>().lambda()
                            .eq(CommentEntity::getOwnerId, id));
                    if (commentEntities.size()!=0){
                        List<Integer> collect = commentEntities.stream().map(CommentEntity::getId).collect(Collectors.toList());
                        commentMapper.deleteUsrLike(collect);
                        commentMapper.delteArticle(id);
                    }
                } catch (Exception e) {
                    throw new KBlogException(ResultCode.DATA_DELTE_ERR);
                }

            } else {
                throw new UnauthorizedException();
            }
        }else {
            throw  new KBlogException(ResultCode.DATA_DELTE_ERR);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result publishArticle(ArticleUploadBo articleUploadBo) {
        String userName = KblogUtils.getUserName();
        Integer id = userMapper.getIdByAccount(userName);
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setAuthorId(id).setContent(articleUploadBo.getContent())
                .setContentFormat(articleUploadBo.getContentHtml())
                .setTitle(articleUploadBo.getTitle())
                .setDescription(articleUploadBo.getSummary())
                .setPublish("0")
                .setCommentNum(0);
        int i = articleMapper.insert(articleEntity);
        if (i == 0) {
            throw new KBlogException(ResultCode.DATA_INSERT_ERR);
        }
        List<Integer> tags = articleUploadBo.getTags();
        tags.add(0, Integer.parseInt(articleUploadBo.getCategory()));
        int flag = categoryMapper.insertRelation(articleEntity.getId(), tags);
        if (flag == 0) {
            throw new KBlogException(ResultCode.DATA_INSERT_ERR);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result updateArticle(ArticleUploadBo articleUploadBo) {
        String userName = KblogUtils.getUserName();
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount, userName));
        ArticleEntity articleEntity = articleMapper.selectById(articleUploadBo.getId());
        if (!BeanUtil.isEmpty(userEntity) && !BeanUtil.isEmpty(articleEntity)) {
            if (userEntity.getId() == articleEntity.getAuthorId()) {
                ArticleEntity articleEntitynew = new ArticleEntity();
                BeanUtil.copyProperties(articleUploadBo, articleEntitynew);
                int i = articleMapper.update(articleEntitynew, new QueryWrapper<ArticleEntity>()
                        .lambda().eq(ArticleEntity::getId, articleEntitynew.getId()));
                if (i == 0) {
                    throw new KBlogException(ResultCode.DATA_DELTE_ERR);
                }
                //修改文章分类
                categoryMapper.deleteArticle(articleUploadBo.getId());
                List<Integer> tags = articleUploadBo.getTags();
                tags.add(0, Integer.parseInt(articleUploadBo.getCategory()));
                int flag = categoryMapper.insertRelation(articleEntity.getId(), tags);
                if (flag == 0) {
                    throw new KBlogException(ResultCode.DATA_INSERT_ERR);
                }
            }
        } else {
            throw new KBlogException(ResultCode.RESULE_DATA_NONE);
        }
        return Result.success();
    }

    @Override
    public Result getRecommend() {
        List<ArticleEntity> articleEntities = articleMapper.selectList(new QueryWrapper<ArticleEntity>()
                .eq("recommend", 1).
                        orderByDesc("readNum")
                .select("id", "title", "readNum")
                .last("limit 5"));
        List<ArticleViewVo> articleViewVos = new ArrayList<>();
        for (ArticleEntity articleEntity : articleEntities) {
            ArticleViewVo articleViewVo = new ArticleViewVo();
            BeanUtil.copyProperties(articleEntity, articleViewVo);
            articleViewVos.add(articleViewVo);
        }
        return Result.success(articleViewVos);
    }

    @Override
    public Result getEditArticle(Integer id) {
        String userName = KblogUtils.getUserName();
        Integer userId = userMapper.getIdByAccount(userName);
        ArticleEntity articleEntity = articleMapper.selectOne(new QueryWrapper<ArticleEntity>().lambda()
                .eq(ArticleEntity::getId, id));
        if (articleEntity==null){
            return Result.error(ResultCode.RESULE_DATA_NONE);
        }
        if (articleEntity.getAuthorId()!=userId){
            throw new  UnauthorizedException();
        }
        return Result.success(this.getEditVo(id));
    }

    private ArticleEditVo getEditVo(Integer id){
        ArticleEntity articleEntity = articleMapper.selectById(id);
        ArticleEditVo articleEditVo=new ArticleEditVo();
        articleEditVo.setId(articleEntity.getId());
        articleEditVo.setContent(articleEntity.getContent());
        articleEditVo.setTitle(articleEntity.getTitle());
        articleEditVo.setSummary(articleEntity.getDescription());
        CategoryShortVo categoryShortVo=  articleMapper.selectCategory(id);
        articleEditVo.setCategory(categoryShortVo);
        List<CategoryShortVo> categoryShortVos=  articleMapper.selectTags(id);
        articleEditVo.setTags(categoryShortVos);
        return  articleEditVo;
    }

    @Override
    public List<Recent> getRecentArticle() {
        return articleMapper.getRecentArticle();
    }

    @Override
    public Result getAll(ArticleShortBo articleShortBo) {
        Page<ArticleAllVo> page = new Page<>(articleShortBo.getPageNum(), articleShortBo.getPageSize());
        page = (Page<ArticleAllVo>) articleMapper.selectShortByTagOrNickname(page, articleShortBo);
        //查询文章的分类
        List<ArticleAllVo> articleAllVos = page.getRecords();
        for (ArticleAllVo articleAllVo : articleAllVos) {
            List<CategoryNameVo> categoryNameVos = categoryMapper.selectByArticleId(articleAllVo.getId());
            List<CategoryShortVo> categoryShortVos = categoryNameVos.stream().map(nameVo -> {
                CategoryShortVo temp = new CategoryShortVo();
                BeanUtil.copyProperties(nameVo, temp);
                return temp;
            }).collect(Collectors.toList());
            articleAllVo.setCategorys(categoryShortVos);
        }
        page.setRecords(articleAllVos);
        MyPage<ArticleAllVo> myPage = new MyPage<ArticleAllVo>().getMyPage(page);
        return Result.success(myPage);
    }


    @Override
    public Result delteArticleByAdmin(Integer id) {
        return null;
    }
}
