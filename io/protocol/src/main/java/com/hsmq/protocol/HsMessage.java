package com.hsmq.protocol;

import com.hsmq.data.Message;
import com.hsmq.utils.ObjectByteUtils;

import java.lang.reflect.Array;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 2:25 下午
 */
public class HsMessage<T> {

    public static void main(String[] args) {

        HsMessage hsMessage = new HsMessage();

        hsMessage.appendArray(new byte[]{1,2,3});
        hsMessage.appendArray(new byte[]{4,5,6});
        for (byte b : hsMessage.getDataArray()) {
            System.out.println(b);
        }

        Message message = new Message();
        message.setTopic("TopicA");
        message.setTag("TagA");
        message.setBody("撒撒打算打算打算打算打算打算的撒打算打算的");

        HsMessage<Message> hsMessage2 = new HsMessage<>(message);

    }


    private Integer length;
    private T data;
    private byte[] dataArray;

    public boolean isNotNull(){
        return length!=null&&dataArray!=null;
    }

    public void setNull(){
        length = null;
        data = null;
        dataArray = null;
    }

    public boolean arrayFinish(){
        return isNotNull()&&length == dataArray.length;
    }

    public void appendArray(byte[] addArray){
        if (addArray==null||addArray.length==0){
            return;
        }
        if (dataArray==null){
            this.dataArray = addArray;
        }else{
            Class<?> type1 = dataArray.getClass().getComponentType();
            byte[] joinedArray = (byte[]) Array.newInstance(type1, dataArray.length + addArray.length);
            System.arraycopy(dataArray, 0, joinedArray, 0, dataArray.length);
            System.arraycopy(addArray, 0, joinedArray, dataArray.length, addArray.length);
            this.dataArray = joinedArray;
        }
    }

    public HsMessage(T data) {
        setData(data);
    }

    public HsMessage() {
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public T toData() {
        if (data!=null){
            return data;
        }
        if (dataArray==null){
            return null;
        }
       return  (T) ObjectByteUtils.toObject(dataArray);
    }

    public void setData(T data) {
        this.data = data;
        byte[] bytes = ObjectByteUtils.toByteArray(data);
        this.dataArray = bytes;
        this.length = bytes.length;
    }

    public byte[] getDataArray() {
        return dataArray;
    }
}
