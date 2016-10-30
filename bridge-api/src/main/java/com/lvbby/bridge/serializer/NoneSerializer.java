package com.lvbby.bridge.serializer;

/**
 * Created by lipeng on 16/10/30.
 */
public class NoneSerializer implements Serializer {
    @Override
    public Object serialize(Object object) {
        return object;
    }
}
