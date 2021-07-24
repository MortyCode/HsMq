package com.hsmq.data;

import com.hsmq.enums.ResultEnum;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 8:12 下午
 */
public class HsResp<T> implements Serializable {

    private static final long serialVersionUID = -20210610L;


    private Integer result;

    private String operation;

    private boolean success = true;

    private T data;

    private List<T> datas;

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

    public List<T> getDatas() {
        return datas;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public static HsResp<Void> typeError(){
        HsResp<Void> resp = new HsResp<>();
        resp.setResult(ResultEnum.ParameterWrongType.getCode());
        resp.setSuccess(false);
        return resp;
    }

    public static HsResp<Void> topicNotExistsError(){
        HsResp<Void> resp = new HsResp<>();
        resp.setResult(ResultEnum.TopicNotExists.getCode());
        resp.setSuccess(false);
        return resp;
    }

    public static HsResp<Void> error(ResultEnum resultEnum){
        HsResp<Void> resp = new HsResp<>();
        resp.setResult(ResultEnum.ParameterWrongType.getCode());
        resp.setSuccess(false);
        return resp;
    }
}
