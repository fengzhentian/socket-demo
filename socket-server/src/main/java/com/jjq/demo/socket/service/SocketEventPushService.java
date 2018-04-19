package com.jjq.demo.socket.service;

import java.net.Socket;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jjq.demo.common.constants.SocketServerGlobal;
import com.jjq.demo.socket.Connection;
import com.jjq.demo.socket.domain.SocketClientData;
import com.jjq.demo.socket.vo.SocketPushRequest;

@Service
public class SocketEventPushService {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 推送socket客户端实时数据
     */
    public void doPushMessage(SocketPushRequest request){
        Iterator<Entry<Socket, SocketClientData>> it = SocketServerGlobal.SCOKET_CLIENTS.entrySet().iterator();  
        while (it.hasNext()) {
            Entry<Socket, SocketClientData> entry = it.next();

            Socket socket = entry.getKey();
            SocketClientData data = entry.getValue();

            // 只推送对应的Socket客户端
            if (StringUtils.equals(data.getClientCode(), request.getClientCode())) {
                // 推送消息
                int res = this.doSendMessage(socket, request.getMessage());
                if (res == 1) {
                    logger.debug("======Socket客户端{}:{}推送消息失败", socket.getInetAddress(), socket.getPort());
                    data.stopRunning();
                }
            }

        }
    }

    /**
     * 推送消息
     */
    private int doSendMessage(Socket clientSokcet, String message) {
        try {
            Connection connection = new Connection(clientSokcet);
            connection.sendMessage(message);
            return 0;
        } catch (Exception e) {
            logger.error("======Socket客户端推送消息异常======", e);
            return 1;
        }
    }

}
