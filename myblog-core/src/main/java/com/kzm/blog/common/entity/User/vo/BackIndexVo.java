package com.kzm.blog.common.entity.User.vo;

import com.kzm.blog.common.entity.log.vo.Recent;
import lombok.Data;

import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:40 2020/4/21
 * @Version
 */
@Data
public class BackIndexVo {

    private Long totalVisisCount;

    private Long todayVisitCount;

    private Integer articleCount;

    private List<Recent> totalVisit;

    private List<Recent> newArticle;

}
