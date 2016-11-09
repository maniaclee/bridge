package com.lvbby.bridge.gateway;

import com.lvbby.bridge.api.ParamsParser;
import com.lvbby.bridge.api.param.parser.InjectParamParser;
import com.lvbby.bridge.gateway.impl.BridgeServer;

/**
 * Created by peng on 16/9/22.
 */
public class Bridge extends BridgeServer {
    /***
     * add inject value function
     *
     * @param request
     * @return
     */
    @Override
    public ParamsParser paramsParser(Request request) {
        return new InjectParamParser(super.paramsParser(request));
    }

    @Override
    public Object proxy(Request request) throws Exception {
        try {
            return super.proxy(request);
        } finally {
            InjectProcessor.clear();//clear
        }
    }
}
