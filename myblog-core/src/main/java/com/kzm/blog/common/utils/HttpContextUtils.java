package com.kzm.blog.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:00 2020/2/26
 * @Version
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServeltRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServeltRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        HttpServletRequest request = getHttpServeltRequest();
        return request.getHeader("origin");
    }

}
