package com.lvbby.bridge.spring;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.exception.BridgeRunTimeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.http.HttpProxy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by peng on 2016/9/27.
 */

public class BridgeController {

    @Autowired
    private Bridge bridge;

    private HttpProxy httpProxy;

    @PostConstruct
    public void init() {
        httpProxy = new HttpProxy(bridge);
    }


    public Object proxy(HttpServletRequest request) {
        try {
            return httpProxy.process(request);
        } catch (BridgeException e) {
            throw new BridgeRunTimeException(e);
        }
    }

}
