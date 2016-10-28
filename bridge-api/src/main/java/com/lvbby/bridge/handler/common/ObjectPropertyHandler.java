package com.lvbby.bridge.handler.common;

import com.google.common.collect.Maps;
import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.ApiGateWayPostHandler;
import com.lvbby.bridge.gateway.Context;
import com.lvbby.bridge.util.BridgeUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by lipeng on 16/10/28.
 */
public class ObjectPropertyHandler implements ApiGateWayPostHandler {

    private Map<Class, Map<String, Object>> valueMap = Maps.newHashMap();

    public ObjectPropertyHandler addPropertyMapping(Class clz, String propertyName, Object value) {
        Map<String, Object> propertyMap = valueMap.get(clz);
        if (propertyMap == null) {
            propertyMap = Maps.newHashMap();
            valueMap.put(clz, propertyMap);
        }
        propertyMap.put(propertyName, value);
        return this;
    }

    @Override
    public Object success(Context context, Object result) {
        Map<String, Object> propertyMap = getMap(result);
        if (propertyMap == null)
            return result;

        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            Field field = BridgeUtil.getField(result.getClass(), entry.getKey());
            if (field != null) {
                try {
                    field.set(result, entry.getValue());
                } catch (IllegalAccessException e) {
                    throw new BridgeRunTimeException(e);
                }
            }

        }
        return result;
    }

    private Map<String, Object> getMap(Object test) {
        for (Class clz : valueMap.keySet()) {
            if (BridgeUtil.isInstance(test.getClass(), clz))
                return valueMap.get(clz);
        }
        return null;
    }
}
