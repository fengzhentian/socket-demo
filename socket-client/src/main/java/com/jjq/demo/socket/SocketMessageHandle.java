package com.jjq.demo.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jjq.demo.biz.domain.MessageRequest;
import com.jjq.demo.common.SocketContentType;
import com.jjq.demo.domain.SocketMessageData;

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
            if (SocketContentType.MESSAGE_REQUEST.equals(code)) {
                // 接收请求参数
                SocketMessageData<MessageRequest> request = JSON.parseObject(
                        msg,
                        new TypeReference<SocketMessageData<MessageRequest>>() {
                        });
                logger.info("======Socket客户端处理接收数据{}======", request);

                // TODO 经过后续处理后

            }
        } catch (Exception e) {
            logger.error("======Socket客户端处理接收数据异常======", e);
        }
    }

}
