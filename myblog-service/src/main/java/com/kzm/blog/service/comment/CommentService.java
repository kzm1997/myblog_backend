package com.kzm.blog.service.comment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.entity.comment.CommentEntity;
import com.kzm.blog.common.entity.comment.bo.CommentBaseBo;
import com.kzm.blog.common.entity.comment.bo.CommentListBo;
import com.kzm.blog.common.entity.comment.bo.CommentSonBo;
import com.kzm.blog.common.entity.comment.bo.LikeBo;
import com.kzm.blog.common.entity.comment.vo.CommentVo;

import java.util.List;

public interface CommentService extends IService<CommentEntity> {

    /**
     * 评论
     * @param commentBaseBo
     * @return
     */
    Result saveComment(CommentBaseBo commentBaseBo);

    /**
     * 得到文章所有评论
     * @param commentListBo
     * @return
     */
    Result selectAllComment(CommentListBo commentListBo);

    /**
     * 回复子评论
     * @param commentSonBo
     * @return
     */
    Result saveSonComment(CommentSonBo commentSonBo);

    /**
     * 评论点赞
     * @param likeBo
     * @return
     */
    Result like(LikeBo likeBo);

    /**
     * 删除评论
     * @param id
     * @return
     */
    Result deleteComments(Integer id);

    /**
     * 获取评论折线图
     * @return
     */
    Result getommentLine();
}
