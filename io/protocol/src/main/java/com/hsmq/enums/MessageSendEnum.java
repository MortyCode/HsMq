package com.hsmq.enums;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:20 下午
 */
public enum MessageSendEnum {

    /**
     * 发送成功
     */
    SendOK("SendOK");

    private String code;

    MessageSendEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
