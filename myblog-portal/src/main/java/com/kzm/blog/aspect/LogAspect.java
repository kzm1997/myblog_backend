package com.kzm.blog.aspect;

import com.kzm.blog.common.annotation.Log;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.entity.log.LogEntity;
import com.kzm.blog.common.utils.HttpContextUtils;
import com.kzm.blog.common.utils.IPUtils;
import com.kzm.blog.common.utils.JsonUtils;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.mapper.article.ArticleMapper;
import com.kzm.blog.mapper.log.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kouzm
 * @Description: 日志切面
 * @Date: Created in 21:56 2020/2/26
 * @Version
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private LogMapper logMapper;


    @Pointcut("@annotation(com.kzm.blog.common.annotation.Log)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        //执行目标方法
        Object o = point.proceed();
        long time = System.currentTimeMillis() - start;
        //保存
        this.saveLog(point, time);
        return o;
    }

    private void saveLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        LogEntity log = new LogEntity();
        Log logAnnotation = method.getAnnotation(Log.class);

        log.setAimModule(logAnnotation.module());
        log.setOperation(logAnnotation.operation());

        //获取请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        log.setMethod(className + "." + methodName + "()");

        //获取请求的参数并增加文章阅读数
        if (log.getOperation().trim().equals("获取文章内容")) {
            Object[] args = point.getArgs();
            String id = JsonUtils.toJson(args[0]);
            articleMapper.updateReadNum(id);
        }

        //获取request;
        HttpServletRequest request = HttpContextUtils.getHttpServeltRequest();
        //获取ip地址

        log.setIp(IPUtils.getIpaddr(request));
        log.setCostTime(time);
        log.setCreateTime(LocalDateTime.now());
        String userName = KblogUtils.getUserName();
        log.setAccount(userName);
        logMapper.insert(log);
    }
}
