package com.hsms.mqserver;


import com.hsmq.storage.config.TopicConfig;
import com.hsms.mqserver.config.ServerConfig;
import com.hsms.mqserver.server.MqServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/4 3:46 下午
 */
public class ServerStartup {

    final static Logger log = LoggerFactory.getLogger(ServerStartup.class);

    public static void main(String[] args) {
        start0(args);
    }

    public static void start0(String[] args){
       String start =
               "\n _   _  _____        _____                          \n" +
                       "| | | |/  ___|      /  ___|                         \n" +
                       "| |_| |\\ `--. ______\\ `--.  ___ _ ____   _____ _ __ \n" +
                       "|  _  | `--. \\______|`--. \\/ _ \\ '__\\ \\ / / _ \\ '__|\n" +
                       "| | | |/\\__/ /      /\\__/ /  __/ |   \\ V /  __/ |   \n" +
                       "\\_| |_/\\____/       \\____/ \\___|_|    \\_/ \\___|_|   \n";
       log.info(start);
       //默认配置
       buildConfig();
       //启动Reactor Server
       new MqServerBootstrap().start();
    }


    public static void buildConfig(){
        ServerConfig.Port = 9001;
        ServerConfig.TopicConfig.put("TopicA", TopicConfig.getDefault("TopicA"));
        ServerConfig.TopicConfig.put("TopicB", TopicConfig.getDefault("TopicB"));
    }



}
