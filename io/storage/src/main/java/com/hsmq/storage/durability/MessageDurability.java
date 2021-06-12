package com.hsmq.storage.durability;

import java.util.StringJoiner;

/**
 * @author ：河神
 * @date ：Created in 2021/6/11 4:14 下午
 */
public class MessageDurability {

    private long offset;

    private int length;

    private String tags;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MessageDurability.class.getSimpleName() + "[", "]")
                .add("offset=" + offset)
                .add("length=" + length)
                .add("tags='" + tags + "'")
                .toString();
    }
}
