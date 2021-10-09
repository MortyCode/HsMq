package com.hsms.mqserver.strategy.executors;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsms.mqserver.data.MessageStore;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:52 下午
 */
public abstract class BaseExecutor<T> {

    public static MessageStore messageStore = new MessageStore();

    public abstract HsResp<?> executor(HsReq<T> hsReq);

    public abstract HsResp<?> executor0(HsReq<?> hsReq);

}
