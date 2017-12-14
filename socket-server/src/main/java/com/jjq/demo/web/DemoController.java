package com.jjq.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jjq.demo.biz.domain.MessageRequest;
import com.jjq.demo.common.SocketContentType;
import com.jjq.demo.socket.service.SocketEventPushService;
import com.jjq.demo.utils.BizResultUtil;
import com.jjq.demo.utils.SocketPushRequestUtil;
import com.jjq.demo.web.vo.BizResultVO;
import com.jjq.demo.web.vo.SocketPushRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class DemoController {

    @Autowired
    private SocketEventPushService socketEventPushService;

    @ApiOperation(value = "推送信息")
    @PostMapping("getInfo")
    public BizResultVO<String> getInfo(@RequestBody SocketPushRequest request) {
        socketEventPushService.doPushMessage(request);
        return BizResultUtil.success("HelloWorld");
    }

    @ApiOperation(value = "通知接口")
    @PostMapping("message")
    public BizResultVO<String> message(@RequestBody MessageRequest request) {

        // 向Socket客户端推送消息
        SocketPushRequest socketPushRequest = SocketPushRequestUtil.request(
                request.getClientCode(), SocketContentType.MESSAGE_REQUEST,
                request);
        socketEventPushService.doPushMessage(socketPushRequest);

        return BizResultUtil.success("HelloWorld");
    }

}
