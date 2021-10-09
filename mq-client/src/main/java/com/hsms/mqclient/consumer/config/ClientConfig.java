package com.hsms.mqclient.consumer.config;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/10/9 3:45 下午
 */
public class ClientConfig {

    public ServerConfig server;
    public HashMap<String, List<ConsumerGroup>> consumer;

    public static class ServerConfig {
        public String host = "127.0.0.1";
        public Integer post = 9001;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPost() {
            return post;
        }

        public void setPost(Integer post) {
            this.post = post;
        }
    }

    public static class ConsumerGroup{
        public String topic;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }

    public ServerConfig getServer() {
        return server;
    }

    public void setServer(ServerConfig server) {
        this.server = server;
    }

    public HashMap<String, List<ConsumerGroup>> getConsumer() {
        return consumer;
    }

    public void setConsumer(HashMap<String, List<ConsumerGroup>> consumer) {
        this.consumer = consumer;
    }
}
