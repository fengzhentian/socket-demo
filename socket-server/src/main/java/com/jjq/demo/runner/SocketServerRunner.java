package com.jjq.demo.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.jjq.demo.socket.SocketServer;

@Component
public class SocketServerRunner implements ApplicationRunner {

    @Autowired
    private SocketServer socketServer;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        socketServer.start();
    }

}
