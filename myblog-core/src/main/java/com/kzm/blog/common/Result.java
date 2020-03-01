package com.kzm.blog.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kzm.blog.common.constant.ResultCode;

import java.io.Serializable;

/**
 * @Author: kouzm
 * @Description: 接口结果
 * @Date: Created in 11:11 2020/2/25
 * @Version
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    public static final long serialVersionUID = -2822928619495260423L;


    private Integer status;

    private String message;

    private T data;

    private Result() {
    }


    private Result(int status, String message) {
        this.status = status;
        this.message = message;

    }

    private Result(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setResultCode(ResultCode resultCode) {
        this.setStatus(resultCode.getCode());
        this.setMessage(resultCode.getMessage());
    }


    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResultCode.SUCCESS.getCode();
    }

    public static Result success() {
        Result result = new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static Result error(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static <T> Result<T> error(ResultCode resultCode, T data) {
        Result result = new Result<T>();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }


}
