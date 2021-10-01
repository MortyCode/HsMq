package com.hsmq.consumer.consumer;

import com.hsmq.data.message.PullMessage;

/**
 * @author ：河神
 * @date ：Created in 2021/10/1 2:44 下午
 */
public abstract class AbstractConsumer {

    protected abstract boolean consumeMessage(PullMessage pullMessage);

    public boolean consumer(PullMessage pullMessage){
        //前置处理
        boolean b = consumeMessage(pullMessage);
        //后置处理
        return b;
    }
}
