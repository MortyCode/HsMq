package com.hsmq.enums;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:20 下午
 */
public enum MessageEnum {

    /**
     * 请求
     */
    Req("Req", HsReq.class),
    /**
     * 返回值
     */
    Resp("Resp", HsResp.class);

    private String code;
    private Class clazz;

    MessageEnum(String code, Class clazz) {
        this.code = code;
        this.clazz = clazz;
    }

    public String getCode() {
        return code;
    }

    public static MessageEnum getByCode(String code){
        for (MessageEnum value : values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
