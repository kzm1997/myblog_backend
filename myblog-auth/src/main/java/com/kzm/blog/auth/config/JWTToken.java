package com.kzm.blog.auth.config;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:31 2020/3/5
 * @Version
 */
@Data
public class JWTToken  implements AuthenticationToken {

    private static final long serialVersionUID = 1282057025599826155L;

    private String token;

    private String exipreAt;

    public JWTToken(String token){
        this.token=token;
    }
    public JWTToken(String token,String exipreAt){
        this.token=token;
        this.exipreAt=exipreAt;
    }
    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}