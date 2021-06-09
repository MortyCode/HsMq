package com.hsmq.data;

import com.hsmq.enums.ResultEnum;

import java.io.Serializable;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 8:12 下午
 */
public class HsResp<T> implements Serializable {

    private Integer result;

    private String operation;

    private T data;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getOperation() {
        return operation;
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


    public static HsResp<Void> typeError(){
        HsResp<Void> resp = new HsResp<>();
        resp.setResult(ResultEnum.ParameterWrongType.getCode());
        return resp;
    }

    public static HsResp<Void> error(ResultEnum resultEnum){
        HsResp<Void> resp = new HsResp<>();
        resp.setResult(ResultEnum.ParameterWrongType.getCode());
        return resp;
    }
}
