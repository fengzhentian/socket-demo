package com.jjq.demo.domain;

import com.jjq.demo.enums.SocketMessageType;

/**
 * Socket消息
 * 
 * @author jingjq
 * @version 2017-12-04 10:13:15
 */
public class SocketMessage {

    /**
     * 消息类型，0：心跳，1：消息
     */
    private SocketMessageType type;
    /**
     * 消息内容
     */
    private String msg;

    public static SocketMessage message(String msg) {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setType(SocketMessageType.MESSAGE);
        socketMessage.setMsg(msg);
        return socketMessage;
    }

    public static SocketMessage heartbeat() {
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setType(SocketMessageType.HEARTBEAT);
        return socketMessage;
    }

    public SocketMessageType getType() {
        return type;
    }

    public void setType(SocketMessageType type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SocketMessage [type=" + type + ", msg=" + msg + "]";
    }

}
