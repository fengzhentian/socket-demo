package com.jjq.demo.constants;

public enum ErrorType implements ErrorCode {

    SYSTEM_ERROR("1000", "系统错误"),
    APP_ERROR("1001", "应用错误"),
    BIZ_ERROR("1002", "业务错误:%s"),
    PARAM_NULL("1003", "入参不能为空:[%s]"),
    OBJECT_NOT_EXISTS("1004", "对象不存在:[%s]"),
    OBJECT_BELONG_ERROR("1005", "对象归属错误:[%s]"),
    RESOURCE_NOT_EXISTS("1006", "访问的资源不存在"),
    PARAM_NOT_VALID("1007", "参数校验失败:%s"),

    BIZ_SUCCESS("8888", "成功");

    private String code;
    private String msg;

    private ErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}
