package com.kzm.blog.common.handler;

import com.kzm.blog.common.Result;
import com.kzm.blog.common.constant.ResultCode;
import com.kzm.blog.common.exception.KBlogException;
import com.kzm.blog.common.exception.KBlogParamException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 12:43 2020/3/9
 * @Version
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception e) {
        log.error("系统内部异常,异常信息 {}", e.getMessage());
        e.printStackTrace();
        return Result.error(ResultCode.SYSTEM_INNER_ERROR);
    }

    @ExceptionHandler(value = KBlogException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleKblogException(KBlogException e) {
        log.error("系统错误:{}", e.getMessage());
        return Result.error(e.getSattus(), e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleUnauthorizedException(Exception e){
        log.error("权限不足, {}",e.getMessage());

    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void handleHttpRequestMethodNotSupportedException(Exception e){
        log.error("请求方法错误",e.getMessage());
    }

    @ExceptionHandler(value = KBlogParamException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Result handleKBlogParamException(KBlogParamException e){
        return Result.error(e.getSattus(),e.getMessage());
    }
    


}
