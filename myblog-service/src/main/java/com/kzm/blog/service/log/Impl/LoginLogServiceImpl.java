package com.kzm.blog.service.log.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kzm.blog.common.entity.log.LoginLog;
import com.kzm.blog.common.entity.log.vo.Recent;
import com.kzm.blog.common.utils.AddressUtil;
import com.kzm.blog.common.utils.HttpContextUtils;
import com.kzm.blog.common.utils.IPUtils;
import com.kzm.blog.mapper.log.LoginLogMapper;
import com.kzm.blog.service.log.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:12 2020/4/13
 * @Version
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper,LoginLog> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public Long findTotalVisitCount() {
        return Long.valueOf(loginLogMapper.selectCount(new QueryWrapper<>()));
    }

    @Override
    public Long findTodayVisitCount() {
        return  loginLogMapper.selectTodayCount();
    }

    @Override
    public List<Recent> findLastSevenDaysVisitCount() {
        return loginLogMapper.findLastSevenDaysVisitCount();
    }

    @Override
    @Transactional
    public void saveLoginLog(LoginLog loginLog) {
       loginLog.setLoginTime(LocalDateTime.now());
        HttpServletRequest httpServeltRequest = HttpContextUtils.getHttpServeltRequest();
        String ip=IPUtils.getIpaddr(httpServeltRequest);
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        loginLogMapper.insert(loginLog);
    }
}
