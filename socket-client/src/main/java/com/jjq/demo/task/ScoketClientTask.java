package com.jjq.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jjq.demo.common.constants.SocketClientGlobal;
import com.jjq.demo.common.properties.SocketServerProperties;
import com.jjq.demo.socket.SocketClient;

/**
 * 定时检测socket客户端正常性
 * 
 * @author jingjq
 * @version 2017-12-01 13:46:33
 */
@Component
@EnableScheduling
public class ScoketClientTask {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SocketServerProperties socketServerProperties;

    /**
     * 10秒定时检测socket客户端正常性
     */
    @Scheduled(cron = "0/10 * *  * * ? ")
    public void doCheckSocketAlive() {
        String address = socketServerProperties.getAddress();
        int port = socketServerProperties.getPort();
        if (SocketClientGlobal.SCOKET_CLIENT == null) {
            SocketClientGlobal.SCOKET_CLIENT = new SocketClient(address, port);
        }
        SocketClientGlobal.SCOKET_CLIENT.start();
    }

}
