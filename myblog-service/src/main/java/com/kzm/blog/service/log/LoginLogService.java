package com.kzm.blog.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kzm.blog.common.entity.log.LoginLog;
import com.kzm.blog.common.entity.log.vo.Recent;

import java.util.List;

public interface LoginLogService extends IService<LoginLog> {

    /**
     * 获取系统访问数
     * @return
     */
    Long findTotalVisitCount();

    /**
     * 今日访问量
     * @return
     */
    Long findTodayVisitCount();

    /**
     *查询近日访问数量
     *  * @return
     */
    List<Recent> findLastSevenDaysVisitCount();

    void saveLoginLog(LoginLog loginLog);
}
