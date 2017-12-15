package com.jjq.demo.socket;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jjq.demo.common.SocketContentType;
import com.jjq.demo.domain.SocketMessage;
import com.jjq.demo.domain.SocketMessageData;
import com.jjq.demo.enums.SocketMessageType;
import com.jjq.demo.properties.ClientProperties;
import com.jjq.demo.socket.Connection;
import com.jjq.demo.util.SpringContextHolder;

public class SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    /**
     * Socket服务端地址
     */
    private String address;
    /**
     * Socket服务端端口
     */
    private int port;

    private ClientProperties clientProperties;

    private Socket socket;
    private Connection connection;
    private SocketMessageHandle messageHandle;

    private boolean running = false; // 连接状态
    private long lastSendTime; // 最后一次发送数据的时间

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
        this.clientProperties = SpringContextHolder.getBean("clientProperties");
        this.messageHandle = new SocketMessageHandle();
    }

    public void start() {
        if (running) {
            return;
        }
        try {
            socket = new Socket(address, port);
            connection = new Connection(socket);
            running = true;
            logger.info("======Socket客户端连接服务端{}:{}成功======",
                    socket.getInetAddress(), socket.getPort());

            // 上报信息
            reportInfo();
            // 保持长连接的线程，每隔2秒项服务器发一个保持连接的心跳消息
            new Thread(new KeepAliveWatchDog()).start();
            // 接受消息的线程，处理消息
            new Thread(new ReceiveWatchDog()).start();
        } catch (IOException e) {
            logger.error("======Socket客户端连接服务端异常======", e);
            stop();
        }
    }

    public void stop() {
        if (running)
            running = false;
        try {
            if (socket != null && !socket.isClosed())
                socket.close();
            logger.info("======Socket客户端关闭成功======");
        } catch (IOException e) {
            logger.error("======Socket客户端关闭异常======", e);
        }
    }

    public void reconnect() {
        try {
            socket = new Socket(address, port);
            connection = new Connection(socket);
        } catch (IOException e) {
            logger.error("======Socket客户端重新连接服务端异常======", e);
            stop();
        }
    }

    /**
     * 心跳协议
     * 
     * @throws IOException
     */
    public void sendHeart() throws IOException {
        connection.sendHeartbeat();
    }

    public SocketMessage receiveMessage() throws IOException {
        return connection.receiveMessage();
    }

    /**
     * 上报信息
     */
    public void reportInfo() {
        try {
            SocketMessageData<String> data = new SocketMessageData<>();
            data.setCode(SocketContentType.CLIENT_CODE);
            data.setData(clientProperties.getCode());

            connection.sendMessage(JSON.toJSONString(data));

            logger.info("======Socket客户端上报客户端编号======");
        } catch (IOException e) {
            logger.error("======Socket客户端上报客户端编号异常======", e);
        }
    }

    /**
     * 发送消息
     * 
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        connection.sendMessage(message);
    }

    /**
     * 与服务端心跳机制，连接失败定时重连
     */
    class KeepAliveWatchDog implements Runnable {
        long checkDelay = 10;
        long keepAliveDelay = 2000;

        public void run() {
            while (running) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {
                        SocketClient.this.sendHeart();
                        logger.debug("======Socket客户端发送心跳包======");
                    } catch (IOException e) {
                        logger.error("======Socket客户端发送心跳包异常======", e);
                        SocketClient.this.reconnect();
                    }
                    lastSendTime = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        logger.error("======Socket客户端延时异常======", e);
                        SocketClient.this.stop();
                    }
                }
            }
        }
    }

    class ReceiveWatchDog implements Runnable {
        public void run() {
            while (running) {
                try {
                    SocketMessage socketMessage = SocketClient.this.receiveMessage();
                    if (socketMessage == null)
                        continue;

                    if (SocketMessageType.MESSAGE.equals(socketMessage.getType())) {
                        // 消息包
                        logger.info("======Socket客户端接收信息：{}======", socketMessage.getMsg());

                        // 后续操作
                        messageHandle.handle(socketMessage.getMsg());
                    }
                    else if (SocketMessageType.HEARTBEAT.equals(socketMessage.getType())) {
                        // 心跳包
                        logger.debug("======Socket客户端收到服务器心跳包======");
                    }

                } catch (Exception e) {
                    logger.error("======Socket客户端接收信息异常======", e);
                    SocketClient.this.reconnect();
                }
            }
        }
    }

}