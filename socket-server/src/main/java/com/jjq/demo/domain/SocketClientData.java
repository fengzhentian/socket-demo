package com.jjq.demo.domain;

import com.jjq.demo.socket.ConnectionThread;

/**
 * Socket客户端信息
 * 
 * @author jingjq
 * @version 2017-12-01 14:22:42
 */
public class SocketClientData {

    /**
     * 客户端编号
     */
    private String clientCode;
    /**
     * 最后更新时间
     */
    private Long lastUpdateTime;
    /**
     * 
     */
    private ConnectionThread connectionThread;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public ConnectionThread getConnectionThread() {
        return connectionThread;
    }

    public void setConnectionThread(ConnectionThread connectionThread) {
        this.connectionThread = connectionThread;
    }

    public void stopRunning() {
        getConnectionThread().stopRunning();
    }

    @Override
    public String toString() {
        return "SocketClientData [clientCode=" + clientCode
                + ", lastUpdateTime=" + lastUpdateTime + "]";
    }

}
