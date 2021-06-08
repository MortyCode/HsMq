package com.hsmq.enums;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:20 下午
 */
public enum MessageEnum {

    /**
     * 返回值
     */
    Result("Result"),
    /**
     * 拉取消息
     */
    Pull("Pull"),
    /**
     * 消息
     */
    Message("Message");

    private String code;

    MessageEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
