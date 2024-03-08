package com.adru.codec;

import com.adru.Serializer.ISerializer;
import com.adru.Serializer.SerializeManagement;
import com.adru.enums.Constant;
import com.adru.enums.ReqType;
import com.adru.subassembly.MyHeader;
import com.adru.subassembly.MyProtocol;
import com.adru.subassembly.MyRequest;
import com.adru.subassembly.MyResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class MyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf out, List<Object> list) throws Exception {
        log.info("==========Decoding begins ==============");
        if(out.readableBytes()< Constant.HEAD_TOTAL_LEN){
            //消息长度不够，不需要解析
            return;
        }
        out.markReaderIndex();//标记一个读取数据的索引，后续用来重置。
        short magic=out.readShort(); //读取magic
        if(magic!=Constant.MAGIC){
            throw new IllegalArgumentException("Illegal request parameter 'magic',"+magic);
        }
        byte serialType=out.readByte(); //读取序列化算法类型
        byte reqType=out.readByte(); //请求类型
        long requestId=out.readLong(); //请求消息id
        int dataLength=out.readInt(); //请求数据长度
        //可读区域的字节数小于实际数据长度
        if(out.readableBytes()<dataLength){
            out.resetReaderIndex();
            return;
        }
        //读取消息内容
        byte[] content=new byte[dataLength];
        out.readBytes(content);

        //构建header头信息
        MyHeader myHeader=new MyHeader(magic,serialType,reqType,requestId,dataLength);
        ISerializer serializer= SerializeManagement.getSerializer(serialType);
        ReqType rt=ReqType.findByCode(reqType);
        switch(rt){
            case REQUEST:
                MyRequest request=serializer.deserialize(content, MyRequest.class);
                MyProtocol<MyRequest> myProtocol=new MyProtocol<>();
                myProtocol.setMyHeader(myHeader);
                myProtocol.setBody(request);
                list.add(myProtocol);
                break;
            case RESPONSE:
               MyResponse response=serializer.deserialize(content,MyResponse.class);
                MyProtocol<MyResponse> resProtocol=new MyProtocol<>();
                resProtocol.setMyHeader(myHeader);
                resProtocol.setBody(response);
                list.add(resProtocol);
                break;
            default:
                break;
        }
    }
}
