package com.kzm.blog.common.exception;

import com.kzm.blog.common.constant.ResultCode;
import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 17:08 2020/3/8
 * @Version
 */
@Data
public class KBlogException extends RuntimeException {

    private int sattus;

    private String mymessgae;


    public KBlogException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.sattus=resultCode.getCode();
        this.mymessgae=resultCode.getMessage();
    }


    private static final long serialVersionUID = -994962710559017255L;

    public KBlogException(String message) {
        super(message);
    }
}
