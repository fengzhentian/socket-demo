package com.jjq.demo.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjq.demo.common.SocketServerGlobal;
import com.jjq.demo.domain.SocketClientData;

public class ListeningThread extends Thread {
    private Logger logger = LoggerFactory.getLogger(ListeningThread.class);

    private ServerSocket serverSocket;
    private Vector<ConnectionThread> connThreads;
    private boolean isRunning;

    public ListeningThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.connThreads = new Vector<ConnectionThread>();
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (serverSocket.isClosed()) {
                stopRunning();
                break;
            }

            try {
                this.logger.info("======Socket服务端开始监听连接请求======");
                Socket clientSocket = serverSocket.accept();

                this.logger.info("======Socket服务端收到{}:{}连接请求======",
                        clientSocket.getInetAddress(), clientSocket.getPort());
                ConnectionThread conn = new ConnectionThread(clientSocket);
                connThreads.addElement(conn);
                conn.start();

                SocketClientData data = new SocketClientData();
                data.setLastUpdateTime(System.currentTimeMillis());
                data.setConnectionThread(conn);
                SocketServerGlobal.SCOKET_CLIENTS.put(clientSocket, data);
            } catch (IOException e) {
                this.logger.error("======Socket服务端监听异常======", e);
            }
        }
    }

    public void stopRunning() {
        for (ConnectionThread connectionThread : connThreads) {
            connectionThread.stopRunning();
        }
        isRunning = false;
    }
}