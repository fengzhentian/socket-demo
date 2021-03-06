package com.jjq.demo.socket;

import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjq.demo.common.constants.SocketServerGlobal;
import com.jjq.demo.common.properties.SocketServerProperties;

@Component
public class SocketServer {
    private Logger logger = LoggerFactory.getLogger(SocketServer.class);

    @Autowired
    private SocketServerProperties socketServerProperties;

    private ListeningThread listeningThread;

    public void start() {

        try {
            this.logger.info("======Socket服务端启动准备中======");
            int port = socketServerProperties.getPort();
            ServerSocket serverSocket = new ServerSocket(port);
            SocketServerGlobal.SERVER_SOCKET = serverSocket;
            this.logger.info("======Socket服务端启动完成，占用{}端口======", port);
            listeningThread = new ListeningThread(serverSocket);
            listeningThread.start();
        } catch (IOException e) {
            this.logger.error("======Socket服务端启动异常======", e);
        }
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        try {
            ServerSocket serverSocket = SocketServerGlobal.SERVER_SOCKET;
            if (serverSocket != null && !serverSocket.isClosed()) {
                listeningThread.stopRunning();
                listeningThread.suspend();
                listeningThread.stop();

                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}