package com.jjq.demo.enums;

/**
 * Socket消息类型
 * 
 * @author jingjq
 * @version 2017-12-04 18:54:02
 */
public enum SocketMessageType {

    HEARTBEAT("0", "心跳包"),
    MESSAGE("1", "消息包"),
    ;

    private String code;
    private String msg;

    private SocketMessageType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
