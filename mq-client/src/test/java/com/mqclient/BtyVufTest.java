package com.mqclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author ：河神
 * @date ：Created in 2021/10/20 4:34 下午
 */
public class BtyVufTest {

    public static void main(String[] args) {


        CompositeByteBuf compositeByteBuf = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, 10);

        ByteBuf buffer = Unpooled.buffer(12);
        buffer.writeBytes("测试".getBytes());
        compositeByteBuf.addComponent(buffer);

        ByteBuf buffer2 = Unpooled.buffer(12);
        buffer2.writeBytes("同学".getBytes());
        compositeByteBuf.addComponent(buffer2);





    }
}
