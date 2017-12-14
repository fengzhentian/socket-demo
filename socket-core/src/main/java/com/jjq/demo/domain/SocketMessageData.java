package com.jjq.demo.domain;

/**
 * Socket消息内容
 * 
 * @author jingjq
 * @version 2017-12-05 09:48:09
 */
public class SocketMessageData<T> {

    /**
     * 消息编号
     */
    private String code;
    /**
     * 消息详细信息
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
