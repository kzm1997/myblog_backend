package com.kzm.blog.common.entity.User.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kzm.blog.common.entity.User.TimeComparator;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: kouzm
 * @Description: 用户关注列表
 * @Date: Created in 23:04 2020/5/18
 * @Version
 */
@Data
public class UserLikeVo  extends TimeComparator {

    private Integer id;

    private String avatar;

    private String nickname;

    private Integer totalWord;

    private Integer likeNum;

    @JsonIgnore
    private Integer toUserId;

    private Boolean likeFlag;

    private String motto;

    private String type;

    private LocalDateTime createTime;




}
