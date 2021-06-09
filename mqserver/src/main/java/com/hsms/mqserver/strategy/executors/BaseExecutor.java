package com.hsms.mqserver.strategy.executors;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.Message;
import com.hsmq.data.Pull;
import com.hsmq.enums.OperationEnum;
import com.hsms.mqserver.data.MessageStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:52 下午
 */
public abstract class BaseExecutor<T,R> {

    public static MessageStore messageStore = new MessageStore();

    public abstract HsResp<R> executor(HsReq<T> hsReq);

    public abstract HsResp<?> executor0(HsReq<?> hsReq);

}
