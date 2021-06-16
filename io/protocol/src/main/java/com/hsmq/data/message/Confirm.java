package com.hsmq.data.message;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 10:50 下午
 */
public class Confirm {

    private int offset;
    private int queueId;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }
}
