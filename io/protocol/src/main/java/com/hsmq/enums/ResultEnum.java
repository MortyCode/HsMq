package com.hsmq.enums;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:20 下午
 */
public enum ResultEnum {

    /**
     * 发送成功
     */
    SendOK("SendOK");

    private String code;

    ResultEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
