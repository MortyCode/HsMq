package com.hsmq.server;

import com.hsmq.server.server.BaseServer;

/**
 * @author ：河神
 * @date ：Created in 2021/6/4 3:46 下午
 */
public class ServerStartup {

    public static void main(String[] args) {
        start(args);
    }

    public static void start(String[] args){
        try {
            BaseServer baseServer = new BaseServer(9001);
            baseServer.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
