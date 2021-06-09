package com.hsmq.enums;

import com.hsmq.data.Message;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 8:38 下午
 */
public enum  OperationEnum {

    /**
     * 发送消息
     */
    Message("Message", Message.class),

    Pull("Pull", Message.class),

    Resp("Resp", null),

    ;

    private String operation;
    private Class clazz;

    OperationEnum(String operation, Class clazz) {
        this.operation = operation;
        this.clazz = clazz;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public static OperationEnum getByCode(String code){
        for (OperationEnum value : values()) {
            if (value.getOperation().equals(code)){
                return value;
            }
        }
        return null;
    }

}
