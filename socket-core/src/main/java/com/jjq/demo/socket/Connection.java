package com.jjq.demo.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.jjq.demo.common.GlobalCons;
import com.jjq.demo.domain.SocketMessage;
import com.jjq.demo.enums.SocketMessageType;

/**
 * Socket通信
 * 
 * @author jingjq
 * @version 2017-12-04 18:54:41
 */
public class Connection {
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (res & 0xff);
        targets[2] = (byte) ((res >> 8) & 0xff);
        targets[1] = (byte) ((res >> 16) & 0xff);
        targets[0] = (byte) (res >>> 24);
        return targets;
    }

    public void sendHeartbeat() throws IOException {
        sendSocketMessage(SocketMessage.heartbeat());
    }

    public void sendMessage(String message) throws IOException {
        sendSocketMessage(SocketMessage.message(message));
    }

    public void sendSocketMessage(SocketMessage socketMessage) throws IOException {

        BufferedOutputStream bos = null;
        bos = new BufferedOutputStream(socket.getOutputStream());

        // socket 下发
        if (SocketMessageType.HEARTBEAT.equals(socketMessage.getType())) {
            // 心跳
            byte[] headArr = int2byte(GlobalCons.TAG_HEART_FLG);
            bos.write(headArr);
            bos.flush();
            return;
        } else if (SocketMessageType.MESSAGE.equals(socketMessage.getType())) {
            // 数据包
            byte[] headArr = int2byte(GlobalCons.TAG_START_FLG);
            byte[] msgArr = socketMessage.getMsg().getBytes(GlobalCons.CONTENT_CHARSET_NAME);
            // json字符串 字节长度
            int msgArrLen = msgArr.length;

            byte[] infoLenArr = int2byte(msgArrLen);
            byte[] TLV = new byte[msgArrLen + 8];

            System.arraycopy(headArr, 0, TLV, 0, 4);
            System.arraycopy(infoLenArr, 0, TLV, 4, 4);
            System.arraycopy(msgArr, 0, TLV, 8, msgArrLen);

            bos.write(TLV);
            bos.flush();
        }

    }

    public String receiveMessage3() throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        return reader.readLine();
    }

    public SocketMessage receiveMessage() throws IOException {

        SocketMessage socketMessage = null;

        int readInt = -1;
        byte[] tagValueBuf = new byte[4];
        byte[] lenArr = new byte[4];
        DataInputStream dis = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
        while ((readInt = dis.read()) != -1) {
            tagValueBuf[0] = tagValueBuf[1];
            tagValueBuf[1] = tagValueBuf[2];
            tagValueBuf[2] = tagValueBuf[3];
            tagValueBuf[3] = (byte) readInt;
            String headVal = "";
            for (int i = 0; i < 4; i++) {
                headVal += Integer.toHexString(tagValueBuf[i] & 0xFF);
            }

            // 0x11aa22bb 开始
            if (GlobalCons.TAG_START.equals(headVal)) {
                // 数据包
                dis.read(lenArr);
                String hex = "";
                for (int i = 0; i < 4; i++) {
                    hex += Integer.toHexString(lenArr[i] & 0xFF);
                }
                int len = Integer.parseInt(hex, 16);

                byte[] bufferArr = new byte[len];

                dis.readFully(bufferArr);

                String msg = new String(bufferArr, GlobalCons.CONTENT_CHARSET_NAME);
                // 消息包
                socketMessage = SocketMessage.message(msg);
                break;
            } else if (GlobalCons.TAG_HEART.equals(headVal)) {
                // 心跳包
                socketMessage = SocketMessage.heartbeat();
                break;
            }
        }

        return socketMessage;
    }

}