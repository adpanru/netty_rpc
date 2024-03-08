package com.adru.handler;

import com.adru.codec.MyDecoder;
import com.adru.codec.MyEnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel serverSocketChannel) throws Exception {
        serverSocketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,12,4,0,0))
                .addLast(new MyDecoder())
                .addLast(new MyEnCoder())
                .addLast(new MyServerHandler());
    }
}
