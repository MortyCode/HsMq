package com.hsmq.protocol;

import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.enums.MessageEnum;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 8:50 下午
 */
public class HsDecodeData {

    private Head head;
    private MessageEnum msgTypeEnum;
    private Object data;

    public HsDecodeData() {
    }

    public HsDecodeData(Head head) {
        this.head = head;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public MessageEnum getMsgTypeEnum() {
        return msgTypeEnum;
    }

    public void setMsgTypeEnum(MessageEnum msgTypeEnum) {
        this.msgTypeEnum = msgTypeEnum;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static HsDecodeData req(HsReq data){
        HsDecodeData respHsDecodeData = new HsDecodeData();
        respHsDecodeData.setHead(Head.toHead(MessageEnum.Resp));
        respHsDecodeData.setData(data);
        return respHsDecodeData;
    }



}
