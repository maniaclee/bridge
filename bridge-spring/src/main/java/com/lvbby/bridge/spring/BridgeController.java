package com.lvbby.bridge.spring;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.exception.BridgeRunTimeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.http.HttpBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by peng on 2016/9/27.
 */

public class BridgeController {

    @Autowired
    private Bridge bridge;

    private HttpBridge httpBridge;

    @PostConstruct
    public void init() {
        httpBridge = new HttpBridge(bridge);
    }


    @RequestMapping
    @ResponseBody
    public Object proxy(HttpServletRequest request) {
        try {
            return httpBridge.process(request);
        } catch (BridgeException e) {
            throw new BridgeRunTimeException(e);
        }
    }

}
