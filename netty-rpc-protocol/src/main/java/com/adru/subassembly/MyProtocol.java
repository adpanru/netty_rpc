package com.adru.subassembly;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyProtocol<T> implements Serializable {
    /**
     *协议头
     */
    private MyHeader myHeader;

    private T body;
}
