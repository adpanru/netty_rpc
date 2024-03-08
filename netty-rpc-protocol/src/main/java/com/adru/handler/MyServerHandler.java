package com.adru.handler;

import com.adru.enums.ReqType;
import com.adru.spring.SpringBeansManager;
import com.adru.subassembly.MyHeader;
import com.adru.subassembly.MyProtocol;
import com.adru.subassembly.MyRequest;
import com.adru.subassembly.MyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class MyServerHandler extends SimpleChannelInboundHandler<MyProtocol<MyRequest>> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接建立成功");
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol<MyRequest> msg) throws Exception {
        MyProtocol myProtocol=new MyProtocol<>();
        MyHeader myHeader=msg.getMyHeader();
        myHeader.setReqType(ReqType.RESPONSE.code());
        Object result=invoke(msg.getBody());
        myProtocol.setMyHeader(myHeader);
       MyResponse response=new MyResponse();
        response.setData(result);
        response.setMsg("success");
        myProtocol.setBody(response);

        ctx.writeAndFlush(myProtocol);
    }

    private Object invoke(MyRequest request){
        try {
            Class<?> clazz=Class.forName(request.getClassName());
            Object bean= SpringBeansManager.getBean(clazz); //获取实例对象(CASE)
            Method declaredMethod=clazz.getDeclaredMethod(request.getMethodName(),request.getParameterTypes());
            return declaredMethod.invoke(bean,request.getParams());
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
