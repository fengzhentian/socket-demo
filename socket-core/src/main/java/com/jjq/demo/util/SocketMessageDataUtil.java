package com.jjq.demo.util;

import com.alibaba.fastjson.JSON;
import com.jjq.demo.domain.SocketMessageData;

public class SocketMessageDataUtil {

    public static <T> String buildMessage(String code, T data) {
        SocketMessageData<T> socketMessageData = new SocketMessageData<T>();
        socketMessageData.setCode(code);
        socketMessageData.setData(data);

        return JSON.toJSONString(socketMessageData);
    }

}
