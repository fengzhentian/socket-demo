package com.jjq.demo.common.vo;

import java.io.Serializable;

import com.jjq.demo.constants.ErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 前台统一返回vo
 * 
 * @author jingjq
 * @version 2017-11-29 10:24:49
 */
@ApiModel(value = "统一响应格式")
public class BizResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码:8888(成功);1000(系统错误);1002(业务错误);1006(访问的url不存在),1007(请求参数不正确)", required = true)
    private String code;
    @ApiModelProperty(value = "错误信息")
    private String message;
    @ApiModelProperty(value = "业务数据")
    private T data;

    public BizResultVO() {
        super();
    }

    public BizResultVO(ErrorCode errorCode, String msg) {
        super();
        this.code = errorCode.getCode();
        this.message = msg;
    }

    public BizResultVO(ErrorCode errorCode) {
        super();
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

}
