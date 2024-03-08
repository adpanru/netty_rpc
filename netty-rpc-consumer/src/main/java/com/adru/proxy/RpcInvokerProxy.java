package com.adru.proxy;

import com.adru.enums.Constant;
import com.adru.enums.ReqType;
import com.adru.enums.SerialType;
import com.adru.subassembly.*;
import com.adru.subject.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private String serviceAddress;
    private int servicePort;

    public RpcInvokerProxy(String serviceAddress, int servicePort) {
        this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin invoke target server");
        //组装参数
        MyProtocol<MyRequest> protocol=new MyProtocol<>();
        long requestId= RequestHolder.REQUEST_ID.incrementAndGet();
        MyHeader header=new MyHeader(Constant.MAGIC, SerialType.JSON_SERIAL.code(), ReqType.REQUEST.code(),requestId,0);
        protocol.setMyHeader(header);
        MyRequest request=new MyRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        protocol.setBody(request);
        //发送请求
        NettyClient nettyClient=new NettyClient(serviceAddress,servicePort);
        //构建异步数据处理
        MyFuture<MyResponse> future=new MyFuture<>(new DefaultPromise<>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId,future);
        nettyClient.sendRequest(protocol);
        return future.getPromise().get().getData();
    }
}

