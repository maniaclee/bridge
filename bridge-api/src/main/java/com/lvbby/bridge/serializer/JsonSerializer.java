package com.lvbby.bridge.serializer;

import com.alibaba.fastjson.JSON;

/**
 * Created by lipeng on 16/10/30.
 */
public class JsonSerializer implements Serializer<String> {
    @Override
    public String serialize(Object object) {
        return JSON.toJSONString(object);
    }
}
