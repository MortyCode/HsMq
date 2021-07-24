package com.hsmq.enums;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:20 下午
 */
public enum ResultEnum {

    /**
     * 发送成功
     */
    SendOK(200,"SendOK"),
    ParameterWrongType(1000,"Wrong Parameter type"),
    TopicNotExists(1001,"Topic Not Exists type"),



    ;

    private Integer code;
    private String dec;

    ResultEnum(Integer code, String dec) {
        this.code = code;
        this.dec = dec;
    }

    public Integer getCode() {
        return code;
    }

    public String getDec() {
        return dec;
    }
}
