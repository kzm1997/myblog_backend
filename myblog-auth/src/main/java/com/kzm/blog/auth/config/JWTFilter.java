package com.kzm.blog.auth.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.kzm.blog.common.constants.Base;
import com.kzm.blog.common.properties.KBlogProperties;
import com.kzm.blog.common.utils.KblogUtils;
import com.kzm.blog.common.utils.SpringContextUtil;
import com.kzm.blog.common.utils.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 18:05 2020/3/5
 * @Version
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {



    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 对跨域提供支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        KBlogProperties kBlogProperties = SpringContextUtil.getBean(KBlogProperties.class);
        String[] anonUrl = StringUtils.splitByWholeSeparatorPreserveAllTokens(kBlogProperties.getAnnonUrl(), StringPool.COMMA);

        boolean match = false;
        for (String u : anonUrl) {
            if (pathMatcher.match(u, httpServletRequest.getRequestURI()))
                match = true;
        }
        if (match) return true;
        if (isLoginAttempt(request, response)) {
            return executeLogin(request, response);
        }
        return false;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(Base.TOKEN);
        return token != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(Base.TOKEN);
        JWTToken jwtToken = new JWTToken(KblogUtils.decryptToken(token));
        try {
            //走shiro的流程
            getSubject(request, response).login(jwtToken);
            return true;
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        log.debug("Authentication required: sending 401 Authentication challenge response.");
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        final String message = "未认证,请在前端系统进行认证";
        try (PrintWriter writer = httpServletResponse.getWriter()) {
            String responseJson = "{\"message\":\"" + message + "\"}";
            writer.print(responseJson);
        } catch (IOException e) {
            log.error("sendChallenge: ", e);
        }
        return false;
    }
}
