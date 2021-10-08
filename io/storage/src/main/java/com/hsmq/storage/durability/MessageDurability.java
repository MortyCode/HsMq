package com.hsmq.storage.durability;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author ：河神
 * @date ：Created in 2021/6/11 4:14 下午
 */
public class MessageDurability implements Serializable {


    private static final long serialVersionUID = -7827672105039234315L;
    private long offset;

    private int length;

    private int tagHashcode;

    private long index;

    public MessageDurability() {
    }

    public MessageDurability(long offset, int length, int tagHashcode) {
        this.offset = offset;
        this.length = length;
        this.tagHashcode = tagHashcode;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTagHashcode() {
        return tagHashcode;
    }

    public void setTagHashcode(int tagHashcode) {
        this.tagHashcode = tagHashcode;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MessageDurability.class.getSimpleName() + "[", "]")
                .add("offset=" + offset)
                .add("length=" + length)
                .add("tagHashcode=" + tagHashcode)
                .add("index=" + index)
                .toString();
    }
}
