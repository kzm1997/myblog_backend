package com.kzm.blog.auth.config;

import com.kzm.blog.auth.Realm.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @Author: kouzm
 * @Description: shiro配置
 * @Date: Created in 22:23 2020/2/29
 * @Version
 */
@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //在shiro过滤器链上加入JWTFilter
        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt",new JWTFilter());
        shiroFilterFactoryBean.setFilters(filters);

        LinkedHashMap<String,String> filterChainDefinitionMap=new LinkedHashMap<>();
        //所有请求都要经过,jwt过滤器
        filterChainDefinitionMap.put("/**","jwt");

         shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
         return  shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public ShiroRealm shiroRealm(){
        //配置Realm;
        return new ShiroRealm();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
         AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();
         authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
         return authorizationAttributeSourceAdvisor;
    }




}
