package com.adru.subassembly;

import io.netty.util.concurrent.Promise;
import lombok.Data;

@Data
public class MyFuture <T>{
    //Promise是可写的 Future, Future自身并没有写操作相关的接口,
    // Netty通过 Promise对 Future进行扩展,用于设置IO操作的结果
    private Promise<T> promise;

    public MyFuture(Promise<T> promise) {
        this.promise = promise;
    }
}
