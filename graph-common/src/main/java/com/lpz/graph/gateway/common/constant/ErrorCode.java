package com.lpz.graph.gateway.common.constant;

/**
 * 定义系统基础类型错误码
 */
public enum ErrorCode implements ErrorCodeI {

    // 系统类错误码
    SUCCESSED(0, "success"),
    FAILED(-1, "fail"),
    FORBIDDEN_REQUEST(403, "无权限错误"),
    NOT_FOUND(404, "未知服务"),
    INTENAL_ERROR(500, "内部服务错误"),

    NOT_LOGIN_ERROR(999, "user not login"),

    PARAM_ERROR(1000, "请求参数校验错误"),
    MEDIA_TYPE_ERROR(1001, "Media Type 错误"),

    BIZ_ERROR(1100, "业务逻辑错误"),
    SYS_ERROR(1200, "未知的其它系统错误"),;


    private Integer code;
    private String errMessage;

    ErrorCode(Integer code, String errMessage) {
        this.code = code;
        this.errMessage = errMessage;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.errMessage;
    }

}
