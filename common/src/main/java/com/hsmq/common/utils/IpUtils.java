package com.hsmq.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ：河神
 * @date ：Created in 2021/7/10 4:11 下午
 */
public class IpUtils {

    public static String getIp(){
        try {
            return  InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


}
