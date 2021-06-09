package com.hsmq.data;

import com.hsmq.enums.OperationEnum;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 8:12 下午
 */
public class HsReq<T> implements Serializable {

    /**
     * @see OperationEnum
     */
    private String operation;

    private T data;

    public String getOperation() {
        return operation;
    }

    public OperationEnum getOperationEnum() {
        return OperationEnum.getByCode(operation);
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
