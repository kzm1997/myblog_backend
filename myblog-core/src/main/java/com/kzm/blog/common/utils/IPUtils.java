package com.kzm.blog.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 23:07 2020/2/26
 * @Version
 */
public class IPUtils {

    private static Logger logger= LoggerFactory.getLogger(IPUtils.class);

    /**
     * 获取IP地址
     * 使用Nginx等反向代理,则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理,X-Forwarded-For的值并不止一个,而是一串IP地址,X-Forwarded-For中的第一个非unknow的有效ip地址
      * @param request
     * @return
     */

    public static String getIpaddr(HttpServletRequest request){
        String ip=null;
         ip = request.getHeader("x-forwarded-for");
        try {
            if (StringUtils.isEmpty(ip)||"unknow".equalsIgnoreCase(ip)){
                ip=request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip)||"unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip)||"unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip)||"unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip)||"unknown".equalsIgnoreCase(ip)){
                ip=request.getRemoteAddr();
            }
        } catch (Exception e) {
            logger.error("iputils error "+e);
        }
       return  "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;

    }

}
