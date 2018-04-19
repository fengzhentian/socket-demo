package com.jjq.demo.socket.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjq.demo.common.SocketContentType;

/**
 * Socket消息处理
 * 
 * @author jingjq
 * @version 2018-04-13 21:14:34
 */
public class SocketMessageHandle {
    private static final Logger logger = LoggerFactory
            .getLogger(SocketMessageHandle.class);

    public SocketMessageHandle() {
        super();
    }

    /**
     * 处理接收到的数据
     * 
     * @param msg
     */
    public void handle(String msg) {
        try {
            JSONObject json = JSON.parseObject(msg);
            String code = json.getString("code");
            if (SocketContentType.CLIENT_CONFIRM.equals(code)) {
                // 接收请求参数
            }
        } catch (Exception e) {
            logger.error("======Socket服务端处理接收数据异常======", e);
        }
    }

}
