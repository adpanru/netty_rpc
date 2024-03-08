package com.adru.Serializer;

public interface ISerializer {
    /**
     * 对对象进行序列化
     * @param object
     * @return {@link byte[]}
     */
    <T> byte[] serialize(T object);

    /**
     * 对对象进行反序列化
     * @param data
     * @param clazz
     * @return {@link T}
     */
    <T> T deserialize(byte[] data,Class<T> clazz);

    /**
     * 获取序列化类型
     * @return byte
     */
    byte getType();

}
