package com.lvbby.bridge.serializer;

/**
 * Created by lipeng on 16/10/30.
 */
public interface Serializer<T> {
    T serialize(Object object);
}
