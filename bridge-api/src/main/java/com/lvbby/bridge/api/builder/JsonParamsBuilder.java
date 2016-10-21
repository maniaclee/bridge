package com.lvbby.bridge.api.builder;

import com.lvbby.bridge.api.Param;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.api.ParamsBuilder;

/**
 * Created by lipeng on 16/10/20.
 */
//TODO
public class JsonParamsBuilder implements ParamsBuilder {
    @Override
    public Params of(Object arg) {
        if (arg == null)
            return new Params(null);
        if (arg instanceof Object[]) {
            Object[] objects = (Object[]) arg;
            Param[] params = new Param[objects.length];
            for (int i = 0; i < objects.length; i++)
                params[i] = new Param(objects[i]);
            return new Params(params);
        }
        return null;
    }
}