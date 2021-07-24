package com.hsmq.common.utils;

import java.lang.management.ManagementFactory;

/**
 * SnowFlake 算法 生产messageID
 * @author ：河神
 * @date ：Created in 2021/7/10 3:53 下午
 */
public class MessageIdUtils {

    public static String newMsgId(String topic){
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name.split("@")[0]);
        String s = name.split("@")[0];
        return topic+s+System.nanoTime();
    }

}
