package com.kzm.blog.service.comment.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.Result;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.User.UserEntity;
import com.kzm.blog.common.entity.comment.CommentEntity;
import com.kzm.blog.common.entity.comment.CommentUserLikeEntity;
import com.kzm.blog.common.entity.comment.bo.CommentBaseBo;
import com.kzm.blog.common.entity.comment.bo.CommentSonBo;
import com.kzm.blog.common.entity.comment.bo.LikeBo;
import com.kzm.blog.common.entity.comment.vo.CommentSonVo;
import com.kzm.blog.common.entity.comment.vo.CommentVo;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.mapper.comment.CommentMapper;
import com.kzm.blog.mapper.comment.CommentUserLikeMapper;
import com.kzm.blog.mapper.user.UserMapper;
import com.kzm.blog.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 18:56 2020/4/6
 * @Version
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentEntity> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentUserLikeMapper commentUserLikeMapper;

    @Override
    @Transactional
    public Result saveComment(CommentBaseBo commentBaseBo) {
        String userName = KblogUtils.getUserName();
        Integer id = userMapper.getIdByAccount(userName);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setOwnerId(commentBaseBo.getId());
        commentEntity.setContent(commentBaseBo.getContent());
        commentEntity.setFromId(id);
        commentEntity.setTold(id);
        commentEntity.setParentId(0);
        commentEntity.setLikeNum(0);
        int i = commentMapper.insert(commentEntity);
        if (i == 0) {
            throw new KBlogException(ResultCode.DATA_INSERT_ERR);
        }
        //获取评论
        List<CommentVo> commentVos = this.selectAllComment(commentBaseBo.getId());
        return Result.success(commentVos);
    }

    @Override
    public List<CommentVo> selectAllComment(Integer id) {
        String userName = KblogUtils.getUserName();
        Integer userId = userMapper.getIdByAccount(userName);
        //获取文章下的顶级评论
        List<CommentEntity> commentEntities = commentMapper.selectList(new QueryWrapper<CommentEntity>().lambda().eq(CommentEntity::getOwnerId, id)
                .eq(CommentEntity::getParentId, 0).orderByDesc(CommentEntity::getCreateTime));
        List<CommentVo> collect = commentEntities.stream().map(getCommentVo).collect(Collectors.toList());
        //设置是否点赞
        List<CommentUserLikeEntity> userLikeEntities = commentUserLikeMapper.selectList(new QueryWrapper<CommentUserLikeEntity>().lambda()
                .eq(CommentUserLikeEntity::getUserId, userId));
        for (CommentVo commentVo : collect) {
            boolean b = userLikeEntities.contains(new CommentUserLikeEntity().setStatus(1)
                    .setCommentId(commentVo.getId())
                    .setUserId(userId));
            commentVo.setLike(b);
        }
        for (CommentVo commentVo : collect) {
            List<CommentEntity> entityList = commentMapper.selectList(new QueryWrapper<CommentEntity>().lambda()
                    .eq(CommentEntity::getParentId, commentVo.getId()).orderByAsc(CommentEntity::getCreateTime));

            List<CommentSonVo> commentSonVos = entityList.stream().map(getCommentSonVo).collect(Collectors.toList());
            commentVo.setReply(commentSonVos);
        }
        return collect;
    }

    private Function<CommentEntity, CommentSonVo> getCommentSonVo = (entity) -> {
        CommentSonVo commentSonVo = new CommentSonVo();
        UserEntity formUser = userMapper.selectById(entity.getFromId());
        UserEntity tomUser = userMapper.selectById(entity.getTold());
        commentSonVo.setId(entity.getId())
                .setContent(entity.getContent())
                .setDate(entity.getCreateTime())
                .setFromId(entity.getFromId())
                .setFromName(formUser.getNickname())
                .setFromAvatar(formUser.getAvatar())
                .setToId(entity.getTold())
                .setToName(tomUser.getNickname())
                .setToAvatar(tomUser.getAvatar());
        return commentSonVo;
    };
    private Function<CommentEntity, CommentVo> getCommentVo = (entity) -> {
        String userName = KblogUtils.getUserName();
        Integer userId = userMapper.getIdByAccount(userName);
        CommentVo commentVo = new CommentVo();
        UserEntity formUser = userMapper.selectById(entity.getFromId());
        UserEntity tomUser = userMapper.selectById(entity.getTold());
        commentVo.setId(entity.getId())
                .setContent(entity.getContent())
                .setDate(entity.getCreateTime())
                .setFromId(entity.getFromId())
                .setFromName(formUser.getNickname())
                .setFromAvatar(formUser.getAvatar())
                .setToId(entity.getTold())
                .setToName(tomUser.getNickname())
                .setToAvatar(tomUser.getAvatar())
                .setLikeNum(entity.getLikeNum());
        return commentVo;
    };

    @Override
    public Result saveSonComment(CommentSonBo commentSonBo) {
        String userName = KblogUtils.getUserName();
        Integer id = userMapper.getIdByAccount(userName);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentSonBo.getContent())
                .setParentId(commentSonBo.getParentId())
                .setOwnerId(commentSonBo.getId())
                .setTold(commentSonBo.getToId())
                .setFromId(id)
                .setLikeNum(0);
        int i = commentMapper.insert(commentEntity);
        if (i == 0) {
            throw new KBlogException(ResultCode.DATA_INSERT_ERR);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result like(LikeBo likeBo) {
        String userName = KblogUtils.getUserName();
        Integer userId = userMapper.getIdByAccount(userName);
        if (likeBo.getType() == 1) {
            CommentUserLikeEntity commentUserLikeEntity = commentUserLikeMapper.selectOne(new QueryWrapper<CommentUserLikeEntity>().lambda()
                    .eq(CommentUserLikeEntity::getCommentId, likeBo.getCommentId())
                    .eq(CommentUserLikeEntity::getUserId, userId));
            if (commentUserLikeEntity == null) {
                CommentUserLikeEntity entity = new CommentUserLikeEntity();
                entity.setCommentId(likeBo.getCommentId());
                entity.setUserId(userId);
                entity.setLikeTime(LocalDateTime.now());
                entity.setStatus(1);
                int i = commentUserLikeMapper.insert(entity);
                if (i == 0) {
                    throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
                }
                //增加点赞数
                int add = commentMapper.likeNumAdd(likeBo.getCommentId());
                if (add==0){
                    throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
                }
            }else{
                commentUserLikeEntity.setStatus(1);
                commentUserLikeMapper.update(commentUserLikeEntity, new QueryWrapper<CommentUserLikeEntity>()
                .lambda().eq(CommentUserLikeEntity::getId,commentUserLikeEntity.getId()));
            }
        } else if (likeBo.getType() == 0) {
            CommentUserLikeEntity commentUserLikeEntity = commentUserLikeMapper.selectOne(new QueryWrapper<CommentUserLikeEntity>().lambda()
                    .eq(CommentUserLikeEntity::getCommentId, likeBo.getCommentId())
                    .eq(CommentUserLikeEntity::getUserId, userId));
            commentUserLikeEntity.setStatus(0);
            int i = commentUserLikeMapper.update(commentUserLikeEntity, new QueryWrapper<CommentUserLikeEntity>()
                    .lambda().eq(CommentUserLikeEntity::getId, commentUserLikeEntity.getId()));
            if (i == 0) {
                throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
            }
            //减少点赞
            int flag= commentMapper.likeNumReduce(likeBo.getCommentId());
            if (flag==0){
                throw new KBlogException(ResultCode.DATA_UPDATE_ERR);
            }

        }
        return Result.success();
    }
}
