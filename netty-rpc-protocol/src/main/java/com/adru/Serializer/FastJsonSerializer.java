package com.adru.Serializer;

import com.alibaba.fastjson.JSON;
import com.adru.enums.SerialType;

public class FastJsonSerializer implements ISerializer{
    @Override
    public <T> byte[] serialize(T object) {
        return JSON.toJSONString(object).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data),clazz);
    }

    @Override
    public byte getType() {
        return SerialType.JSON_SERIAL.code();
    }
}

