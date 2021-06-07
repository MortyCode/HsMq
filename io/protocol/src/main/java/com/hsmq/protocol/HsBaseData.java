package com.hsmq.protocol;

import java.util.StringJoiner;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:42 下午
 */
public class HsBaseData {

    private Integer length;
    private String body;

    public boolean isNotNull(){
        return length!=null&&body!=null;
    }

    public void setNull(){
        length = null;
        body = null;
    }

    public HsBaseData copy(){
        HsBaseData hsBaseData = new HsBaseData();
        hsBaseData.setBody(body);
        hsBaseData.setLength(length);
        return hsBaseData;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void appendBody(String body){
        if (this.body==null){
            this.body = body;
        }else{
            this.body = this.body+ body;
        }
    }

    public HsBaseData(String body) {
        this.body = body;
        this.length = body.getBytes().length;
    }

    public HsBaseData() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HsBaseData.class.getSimpleName() + "[", "]")
                .add("length=" + length)
                .add("body='" + body + "'")
                .toString();
    }
}
