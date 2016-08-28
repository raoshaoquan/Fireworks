package com.github.rsq.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.List;

/**
 * Created by raoshaoquan on 16/6/2.
 */
public class MessageEncoder extends LengthFieldPrepender {

    public MessageEncoder(int lengthFieldLength) {
        super(lengthFieldLength);
    }

    @Override
    protected void encode(ChannelHandlerContext cxt, ByteBuf msg, List<Object> out) throws Exception {
        super.encode(cxt, msg, out);
        return;
    }

}
