package com.kzm.blog.common.exception;

import com.kzm.blog.common.constant.ResultCode;
import lombok.Data;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 10:25 2020/3/23
 * @Version
 */
@Data
public class KBlogParamException extends RuntimeException {

    private static final long serialVersionUID = -994962720559017155L;

    private int sattus;

    private String mymessgae;

    public KBlogParamException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.sattus = resultCode.getCode();
        this.mymessgae = resultCode.getMessage();
    }


    public KBlogParamException(String message) {
        super(message);
    }
}
