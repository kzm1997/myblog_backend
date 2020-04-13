package com.kzm.blog.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.article.ArticleEntity;
import com.kzm.blog.common.entity.article.bo.ArticleShortBo;
import com.kzm.blog.common.entity.article.bo.ArticleUploadBo;

public interface ArticleService extends IService<ArticleEntity> {

    /**
     * 通过分离id获取文章 0代表全部文章,-1代表热点文章
     * @param articleShortBo
     * @return
     */
    Result getArticleByCategory(ArticleShortBo articleShortBo);

    /**
     * 根据id获取文章简介展示页
     * @param id
     * @return
     */
    Result getArticle(Integer id);

    /**
     * 删除文章
     * @param id
     * @return
     */
    Result delteArticle(Integer id);

    /**
     * 发布文章
     * @param articleUploadBo
     * @return
     */
    Result publishArticle(ArticleUploadBo articleUploadBo);

    /**
     * 更新文章
     * @param articleUploadBo
     * @return
     */
    Result updateArticle(ArticleUploadBo articleUploadBo);

    /**
     * 获取推介文章
     * @return
     */
    Result getRecommend();

    /**
     * 文章编辑页获取文章
     *  @param id
     * @return
     */
    Result getEditArticle(Integer id);
}
