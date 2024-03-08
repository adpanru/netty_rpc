package com.adru.handler;

import com.adru.subassembly.MyFuture;
import com.adru.subassembly.MyProtocol;
import com.adru.subassembly.MyResponse;
import com.adru.subassembly.RequestHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClientHandler extends SimpleChannelInboundHandler<MyProtocol<MyResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol<MyResponse> msg) throws Exception {
        log.info("receive rpc server result");
        long requestId=msg.getMyHeader().getRequestId();
        MyFuture<MyResponse> future= RequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getBody()); //返回结果
    }
}
