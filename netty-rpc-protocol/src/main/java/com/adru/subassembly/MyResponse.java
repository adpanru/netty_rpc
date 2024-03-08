package com.adru.subassembly;

import lombok.Data;

@Data
public class MyResponse {

    /**
     *返回数据
     */
    private Object data;

    /**
     *消息
     */
    private String msg;
}
