package com.github.rsq.gateway;

import com.github.rsq.common.coder.MessageDecoder;
import com.github.rsq.common.coder.MessageEncoder;
import com.github.rsq.common.consts.NetworkConsts;
import com.github.rsq.gateway.handler.FiexLengthServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by raoshaoquan on 16/8/8.
 */
public class GatewayServer {

    public static final int SERVER_PORT = 7001;

    public static void main(String[] args) throws Exception {

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            //p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast("decoder", new MessageDecoder(
                                    NetworkConsts.FRAME_LEN_MAXSIZE,
                                    0,
                                    NetworkConsts.LENGTH_FIELD_LEN));
                            p.addLast("encoder", new MessageEncoder(NetworkConsts.LENGTH_FIELD_LEN));
                            p.addLast("handler", new FiexLengthServerHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(SERVER_PORT).sync();

            System.out.println("=============================\n"
                    + "Server started at port " + SERVER_PORT + "\n"
                    + "=============================\n");

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
