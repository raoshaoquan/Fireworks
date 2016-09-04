package com.github.rsq.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by raoshaoquan on 16/6/2.
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {
    // 第一个参数为信息最大长度，超过这个长度回报异常，
    // 第二参数为长度属性的起始（偏移）位，我们的协议中长度是0到第3个字节，所以这里写0，
    // 第三个参数为“长度属性”的长度，我们是4个字节，所以写4，
    // 第四个参数为长度调节值，在总长被定义为包含包头长度时，修正信息长度，
    // 第五个参数为跳过的字节数，根据需要我们跳过前4个字节，以便接收端直接接受到不含“长度属性”的内容。

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset,
                   int lengthFieldLength) {
        super(ByteOrder.LITTLE_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0, true);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buffer = (ByteBuf)super.decode(ctx, in);
        return buffer;
    }

}

