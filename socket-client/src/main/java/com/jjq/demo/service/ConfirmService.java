package com.jjq.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jjq.demo.biz.domain.MessageRequest;
import com.jjq.demo.common.SocketContentType;
import com.jjq.demo.common.constants.SocketClientGlobal;
import com.jjq.demo.domain.SocketMessageData;

@Service
public class ConfirmService {

    private static final Logger logger = LoggerFactory
            .getLogger(ConfirmService.class);

    public void doConfirm(MessageRequest request) {

        logger.info("======Socket客户端收到信息{}======", JSON.toJSON(request));

        try {
            SocketMessageData<String> data = new SocketMessageData<>();
            data.setCode(SocketContentType.CLIENT_CONFIRM);
            data.setData("消息确认");
            SocketClientGlobal.SCOKET_CLIENT
                    .sendMessage(JSON.toJSONString(data));
        } catch (Exception e) {
        }

    }

}
