package com.hsms.mqserver.server;

import com.hsms.mqserver.config.ServerConfig;
import com.hsms.mqserver.data.ConsumerQueueManger;
import com.hsms.mqserver.reactor.ObjectReactor;
import com.hsms.mqserver.strategy.MessageStrategy;

/**
 * @author ：河神
 * @date ：Created in 2021/7/24 2:04 下午
 */
public class MqServerBootstrap {

    public Thread worker;



    public void start() {
        //启动注册topic
        ServerConfig.topicConfigList.forEach((topic,config)->{
            ConsumerQueueManger.registerTopic(topic);
        });

        //恢复消费组消费序列
        ConsumerQueueManger.recoveryConsumer();
        //MQ配置更改命令




        //启动服务
        worker = new Thread(new ObjectReactor(ServerConfig.port));
        worker.start();
    }
}
