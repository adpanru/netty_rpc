package com.adru.subassembly;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MyHeader implements Serializable {

    /**
     *魔数（2个字节）
     */
    private short magic;

    /**
     *序列化类型（1个字节）
     */
    private byte serialType;

    /**
     *消息类型（1个字节）
     */
    private byte reqType;

    /**
     *请求id（8个字节）
     */
    private long requestId;

    /**
     *数据长度（4个字节）
     */
    private int length;
}
