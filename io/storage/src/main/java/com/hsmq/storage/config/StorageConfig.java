package com.hsmq.storage.config;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 3:32 下午
 */
public class StorageConfig {

    public static final String MessagePath = System.getProperty("user.home")+"/data/";

    public static String rootPath = System.getProperty("user.home")+"/hsmq/";

    public static final String MessageStorage = rootPath+"storage";

    public static final String MessageQueue = rootPath+"message_queue";

    public static final String ConsumerMessageQueue = rootPath+"consumer_message_queue";




}
