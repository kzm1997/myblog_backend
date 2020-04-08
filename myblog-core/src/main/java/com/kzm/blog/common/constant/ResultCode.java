package com.kzm.blog.common.constant;

public enum ResultCode {


    /* 成功状态码 */
    SUCCESS(0, "成功"),
    ERROR(1, "失败"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_ONLY_HAS(10005, "参数唯一性校验失败"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),

    PARAM_NOT_SAME_PASSWORD(10006, "密码确认不一致"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    EMAIL_HAS_EXISTED(2005, "邮箱已使用"),
    USER_HAS_EXISTED(20006, "用户已存在"),
    USeR_PASSWD_ERROR(20008,"密码错误"),
    USER_Register_ERROR(20007, "用户注册错误"),
    USER_AVATAR_ERROR(20008,"头像上传失败"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统内部错误"),
    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002,"数据有误"),
    DATA_ALREADY_EXISTED(50003,"数据已存在"),
    DATA_INSERT_ERR(5004,"数据新增错误"),
    DATA_UPDATE_ERR(5005,"数据更新错误"),
    DATA_DELTE_ERR(5006,"数据删除错误"),

    /* 文件上传： */
    UPLOAD_ERROR(60001,"文件上传失败");


    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

      ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    }
