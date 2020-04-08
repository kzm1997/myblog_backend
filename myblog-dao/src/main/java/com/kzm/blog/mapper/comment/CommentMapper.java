package com.kzm.blog.mapper.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kzm.blog.common.entity.comment.CommentEntity;
import io.lettuce.core.dynamic.annotation.Param;

public interface CommentMapper extends BaseMapper<CommentEntity> {


    int likeNumAdd(@Param("id") Integer id);

    int likeNumReduce(@Param("id") Integer id);
}

