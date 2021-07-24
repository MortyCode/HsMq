package com.hsms.mqserver;


import com.hsms.mqserver.config.ServerConfig;
import com.hsms.mqserver.reactor.ObjectReactor;
import com.hsms.mqserver.server.MqServerBootstrap;

/**
 * @author ：河神
 * @date ：Created in 2021/6/4 3:46 下午
 */
public class ServerStartup {

    public static void main(String[] args) {
        start0(args);
    }

    public static void start0(String[] args){
        try {
            String start =
                    " _   _  _____        _____                          \n" +
                            "| | | |/  ___|      /  ___|                         \n" +
                            "| |_| |\\ `--. ______\\ `--.  ___ _ ____   _____ _ __ \n" +
                            "|  _  | `--. \\______|`--. \\/ _ \\ '__\\ \\ / / _ \\ '__|\n" +
                            "| | | |/\\__/ /      /\\__/ /  __/ |   \\ V /  __/ |   \n" +
                            "\\_| |_/\\____/       \\____/ \\___|_|    \\_/ \\___|_|   \n";
            System.out.println(start);
            buildConfig();
            new MqServerBootstrap().start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void buildConfig(){
        ServerConfig.port = 9001;

    }



}
