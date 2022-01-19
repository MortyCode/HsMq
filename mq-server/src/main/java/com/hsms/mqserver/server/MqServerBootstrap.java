package com.hsms.mqserver.server;

import com.hsms.mqserver.config.ServerConfig;
import com.hsms.mqserver.data.ConsumerQueueManger;
import com.hsms.mqserver.reactor.ObjectReactor;

/**
 * @author ：河神
 * @date ：Created in 2021/7/24 2:04 下午
 */
public class MqServerBootstrap {


    public void start() {
        //启动注册topic
        ServerConfig.TopicConfig.forEach((topic,config)->{
            ConsumerQueueManger.registerTopic(config);
        });
        //启动服务
        new ObjectReactor(ServerConfig.Port).start();

    }
}
