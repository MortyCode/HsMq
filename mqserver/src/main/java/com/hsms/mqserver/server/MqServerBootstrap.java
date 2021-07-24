package com.hsms.mqserver.server;

import com.hsms.mqserver.config.ServerConfig;
import com.hsms.mqserver.reactor.ObjectReactor;
import com.hsms.mqserver.strategy.MessageStrategy;

/**
 * @author ：河神
 * @date ：Created in 2021/7/24 2:04 下午
 */
public class MqServerBootstrap {

    public void start() throws InterruptedException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //启动服务
                    new ObjectReactor(ServerConfig.port).start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
