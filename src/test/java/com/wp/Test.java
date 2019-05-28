package com.wp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class Test {


    public static void main(String[] args) {

        //非池化的
        //组合缓冲区
        CompositeByteBuf byteBufs = Unpooled.compositeBuffer();

        //堆内缓冲区 jvm
        ByteBuf buffer = Unpooled.buffer();
        //堆外缓冲区，直接内存
        ByteBuf directBuffer = Unpooled.directBuffer();
        byteBufs.addComponents(buffer,directBuffer);

        //赤化的
    }
}
