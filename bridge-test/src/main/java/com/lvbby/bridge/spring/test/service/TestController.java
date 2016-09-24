package com.lvbby.bridge.spring.test.service;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.wrapper.Context;
import com.lvbby.bridge.api.wrapper.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by peng on 16/9/24.
 */
@Controller
@RequestMapping("/")
public class TestController {
    @Autowired
    private Bridge bridge;

    @RequestMapping("data/{service}/{method}")
    public void sdff(@PathVariable("service") String service, @PathVariable("method") String method) throws BridgeException {
        bridge.proxy(new Context(service, method, Params.of(new Object[1])));
    }
}
