package com.adru.subject;

import com.adru.handler.ClientInitializer;
import com.adru.subassembly.MyProtocol;
import com.adru.subassembly.MyRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
    private String serviceAddress;
    private int servicePort;
    public NettyClient(String serviceAddress,int servicePort){
        log.info("begin init NettyClient");
        bootstrap=new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());
        this.serviceAddress=serviceAddress;
        this.servicePort=servicePort;
    }

    public void sendRequest(MyProtocol<MyRequest> protocol) throws InterruptedException {
        ChannelFuture future=bootstrap.connect(this.serviceAddress,this.servicePort).sync();
        future.addListener(listener->{
            if(future.isSuccess()){
                log.info("connect rpc server {} success.",this.serviceAddress);
            }else{
                log.error("connect rpc server {} failed .",this.serviceAddress);
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("begin transfer data");
        future.channel().writeAndFlush(protocol);
    }
}
