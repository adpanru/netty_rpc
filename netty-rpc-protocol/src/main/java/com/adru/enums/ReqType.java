package com.adru.enums;

public enum ReqType {

    /**
     *请求
     */
    REQUEST((byte)1),

    /**
     *响应
     */
    RESPONSE((byte)2),

    /**
     *心跳
     */
    HEARTBEAT((byte)3);

    private byte code;

    private ReqType(byte code) {
        this.code=code;
    }

    public byte code(){
        return this.code;
    }
    public static ReqType findByCode(int code) {
        for (ReqType msgType : ReqType.values()) {
            if (msgType.code() == code) {
                return msgType;
            }
        }
        return null;
    }
}
