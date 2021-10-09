package com.hsms.mqclient;

import com.alibaba.fastjson.JSON;
import com.hsms.mqclient.consumer.config.ClientConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author ：河神
 * @date ：Created in 2021/10/9 3:21 下午
 */
public class ConfigTest {
    public static void main(String[] args) throws IOException {

        String url4 = ConfigTest.class.getClassLoader().getResource("").getFile();
        ClientConfig o =
                JSON.parseObject(Files.readAllBytes(Paths.get(url4 + "/hsmq.json")), ClientConfig.class);


        System.out.println(JSON.toJSONString(o));
    }

}
