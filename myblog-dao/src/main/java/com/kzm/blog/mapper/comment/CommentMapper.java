package com.kzm.blog.mapper.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kzm.blog.common.entity.article.vo.ArticleRecentVo;
import com.kzm.blog.common.entity.comment.CommentEntity;
import com.kzm.blog.common.entity.comment.bo.CommentListBo;
import com.kzm.blog.common.entity.log.vo.Recent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<CommentEntity> {


    int likeNumAdd(@Param("id") Integer id);

    int likeNumReduce(@Param("id") Integer id);

    int delteArticle(@Param("id") Integer id);

    int deleteUsrLike(@Param("collect") List<Integer> collect);

    Page<CommentEntity> selectMaps(@Param("page") Page<CommentEntity> page, @Param("commentListBo") CommentListBo commentListBo);

    List<Recent> selectLine();


}

