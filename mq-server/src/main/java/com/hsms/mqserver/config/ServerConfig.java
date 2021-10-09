package com.hsms.mqserver.config;

import com.hsmq.storage.config.TopicConfig;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：河神
 * @date ：Created in 2021/7/24 2:04 下午
 */
public class ServerConfig {

    public static int Port = 9001;

    public static ConcurrentHashMap<String,TopicConfig> TopicConfig = new ConcurrentHashMap<>();

}
