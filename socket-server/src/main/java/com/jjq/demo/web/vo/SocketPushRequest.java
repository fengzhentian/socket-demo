package com.jjq.demo.web.vo;

/**
 * socket推送请求
 * 
 * @author jingjq
 * @version 2017-12-09 15:53:27
 */
public class SocketPushRequest {

    /**
     * 客户端编号
     */
    private String clientCode;
    /**
     * 推送信息
     */
    private String message;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
