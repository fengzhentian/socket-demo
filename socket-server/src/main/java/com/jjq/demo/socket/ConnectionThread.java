package com.jjq.demo.socket;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jjq.demo.common.SocketContentType;
import com.jjq.demo.common.constants.SocketServerGlobal;
import com.jjq.demo.domain.SocketMessage;
import com.jjq.demo.enums.SocketMessageType;
import com.jjq.demo.socket.domain.SocketClientData;
import com.jjq.demo.socket.handle.SocketMessageHandle;

public class ConnectionThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionThread.class);

    private Socket socket;
    private Connection connection;
    private boolean isRunning;

    /**
     * 接收延时30秒
     */
    private static final long receiveTimeDelay = 30 * 1000;
    /**
     * 最后接收时间
     */
    private long lastReceiveTime = System.currentTimeMillis();

    public ConnectionThread(Socket socket) {
        this.socket = socket;
        this.connection = new Connection(socket);
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            // Check whether the socket is closed.
            if (socket.isClosed()) {
                stopRunning();
                break;
            }

            // 超出30秒没有收到信息，关闭socket连接
            if (System.currentTimeMillis()
                    - lastReceiveTime > receiveTimeDelay) {
                stopRunning();
                break;
            }

            try {
                SocketMessage socketMessage = connection.receiveMessage();
                if (socketMessage == null)
                    continue;

                SocketClientData data = SocketServerGlobal.SCOKET_CLIENTS
                        .get(socket);
                if (data == null) {
                    data = new SocketClientData();
                    data.setConnectionThread(this);
                }

                if (SocketMessageType.MESSAGE.equals(socketMessage.getType())) {
                    // 消息包
                    logger.info("======Socket服务端接收信息：{}======", socketMessage.getMsg());

                    // 后续操作
                    try {
                        String msg = socketMessage.getMsg();
                        JSONObject json = JSONObject.parseObject(msg);
                        String code = json.getString("code");
                        if (SocketContentType.CLIENT_CODE.equals(code)) {
                            logger.info("======Socket客户端编号：{}",
                                    json.getString("data"));
                            data.setClientCode(json.getString("data"));
                        } else {
                            SocketMessageHandle messageHandle = new SocketMessageHandle();
                            messageHandle.handle(msg);
                        }
                    } catch (Exception e) {
                        logger.error("======Socket服务端处理接收数据异常======", e);
                    }
                }
                else if (SocketMessageType.HEARTBEAT.equals(socketMessage.getType())) {
                    // 心跳包
                    logger.debug("======Socket服务端收到{}:{}心跳======", socket.getInetAddress(), socket.getPort());
                }

                lastReceiveTime = System.currentTimeMillis();
                data.setLastUpdateTime(lastReceiveTime);
                SocketServerGlobal.SCOKET_CLIENTS.put(socket, data);

                connection.sendHeartbeat();
                logger.debug("======Socket服务端发送心跳包======");

            } catch (IOException e) {
                logger.error("======Socket服务端连接客户端异常======", e);
                stopRunning();
            }
        }
    }

    public void stopRunning() {
        isRunning = false;

        if (socket == null)
            return;

        try {
            socket.close();
            logger.debug("======Socket客户端{}:{}关闭成功", socket.getInetAddress(), socket.getPort());
        } catch (IOException e) {
            logger.error("======Socket客户端{}:{}关闭异常", socket.getInetAddress(), socket.getPort(), e);
        }
        try {
            SocketServerGlobal.SCOKET_CLIENTS.remove(socket);
            logger.info("======Socket客户端{}:{}从客户端列表中移除成功", socket.getInetAddress(), socket.getPort());
        } catch (Exception e) {
            logger.error("======Socket客户端{}:{}从客户端列表中移除异常", socket.getInetAddress(), socket.getPort(), e);
        }
    }
}