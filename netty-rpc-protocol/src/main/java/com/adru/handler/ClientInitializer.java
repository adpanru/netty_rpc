package com.adru.handler;

import com.adru.codec.MyDecoder;
import com.adru.codec.MyEnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("begin initChannel");
        ch.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,12,4,0,0))
                .addLast(new LoggingHandler())
                .addLast(new MyEnCoder())
                .addLast(new MyDecoder())
                .addLast(new MyClientHandler());
    }
}
