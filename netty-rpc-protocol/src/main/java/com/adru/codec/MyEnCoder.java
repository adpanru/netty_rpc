package com.adru.codec;

import com.adru.Serializer.ISerializer;
import com.adru.Serializer.SerializeManagement;
import com.adru.subassembly.MyHeader;
import com.adru.subassembly.MyProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MyEnCoder extends MessageToByteEncoder<MyProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyProtocol<Object> myProtocol, ByteBuf in) throws Exception {
        log.info("=============begin RpcEncoder============");
        MyHeader myHeader=myProtocol.getMyHeader();
        in.writeShort(myHeader.getMagic()); //写入魔数
        in.writeByte(myHeader.getSerialType()); //写入序列化类型
        in.writeByte(myHeader.getReqType());//写入请求类型
        in.writeLong(myHeader.getRequestId()); //写入请求id
        ISerializer serializer= SerializeManagement.getSerializer(myHeader.getSerialType());
        byte[] data=serializer.serialize(myProtocol.getBody()); //序列化
        myHeader.setLength(data.length);
        in.writeInt(data.length); //写入消息长度
        in.writeBytes(data);
    }
}
