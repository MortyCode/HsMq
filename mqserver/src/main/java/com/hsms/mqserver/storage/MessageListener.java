package com.hsms.mqserver.storage;

import com.hsmq.storage.durability.MessageDurability;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ：河神
 * @date ：Created in 2021/7/24 5:18 下午
 */
public class MessageListener {

    private ConcurrentLinkedQueue<MessageDurability> messageMappingQueue =
            new ConcurrentLinkedQueue<>();






}
