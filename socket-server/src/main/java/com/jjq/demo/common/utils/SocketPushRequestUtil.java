package com.jjq.demo.common.utils;

import com.jjq.demo.socket.vo.SocketPushRequest;
import com.jjq.demo.util.SocketMessageDataUtil;

public class SocketPushRequestUtil {

    /**
     * @param clientCode 客户端编号
     * @param code 消息编号
     * @param data 消息内容
     * @return
     */
    public static <T> SocketPushRequest request(String clientCode, String code,
            T data) {

        String message = SocketMessageDataUtil.buildMessage(code, data);

        SocketPushRequest socketPushRequest = new SocketPushRequest();
        socketPushRequest.setClientCode(clientCode);
        socketPushRequest.setMessage(message);

        return socketPushRequest;
    }

}
