package com.kzm.blog.common.exception;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:10 2020/3/9
 * @Version
 */
public class RedisException extends RuntimeException {

    private static final long serialVersionUID = -994962710559013255L;

    public RedisException(String message) {
        super(message);
    }
}
