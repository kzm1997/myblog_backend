package com.kzm.blog.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kzm.blog.common.entity.log.LoginLog;
import com.kzm.blog.common.entity.log.vo.Recent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 查询今日访问数
     * @return
     */
    Long selectTodayCount();

    /**
     * 查询近日访问量
     * @return
     */
    List<Recent> findLastSevenDaysVisitCount();

}
