package com.jjq.demo.common.constants;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jjq.demo.socket.domain.SocketClientData;

/**
 * 全局静态参数配置
 * 
 * @author jingjq
 * @version 2017-12-04 18:46:37
 */
public class SocketServerGlobal {

    /**
     * socket服务端
     */
    public static ServerSocket SERVER_SOCKET;

    /**
     * socket客户端列表
     */
    public static final Map<Socket, SocketClientData> SCOKET_CLIENTS = new ConcurrentHashMap<Socket, SocketClientData>();

}
