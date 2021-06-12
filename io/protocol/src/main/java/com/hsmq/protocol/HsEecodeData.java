package com.hsmq.protocol;

import com.hsmq.data.Head;
import com.hsmq.data.HsResp;
import com.hsmq.enums.MessageEnum;
import com.hsmq.utils.ObjectByteUtils;

import java.io.Serializable;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 8:50 下午
 */
public class HsEecodeData {

    private byte[] head;
    private byte[] data;

    public byte[] getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = ObjectByteUtils.toByteArray(head);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(Serializable data) {
        this.data = ObjectByteUtils.toByteArray(data);
    }

    public int getHeadLength(){
        return head.length;
    }

    public int getDataLength(){
        return data.length;
    }

    public int getLength(){
        return 4 * 2 + getDataLength() + getHeadLength();
    }


    public static HsEecodeData resp(HsResp data){
        HsEecodeData respHsDecodeData = new HsEecodeData();
        respHsDecodeData.setHead(Head.toHead(MessageEnum.Resp));
        respHsDecodeData.setData(data);
        return respHsDecodeData;
    }

}
