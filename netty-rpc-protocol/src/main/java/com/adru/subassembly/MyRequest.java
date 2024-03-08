package com.adru.subassembly;


import lombok.Data;

import java.io.Serializable;

@Data
public class MyRequest implements Serializable {
    /**
     *类名
     */
    private String className;

    /**
     *方法名
     */
    private String methodName;

    /**
     *参数
     */
    private Object[] params;

    /**
     *参数类型
     */
    private Class<?>[] parameterTypes;
}
