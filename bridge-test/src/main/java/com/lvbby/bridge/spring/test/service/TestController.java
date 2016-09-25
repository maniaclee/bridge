package com.lvbby.bridge.spring.test.service;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.http.HttpProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by peng on 16/9/24.
 */
@Controller
@RequestMapping("/")
public class TestController {
    @Autowired
    private Bridge bridge;

    @RequestMapping("data/{service}/{method}")
    @ResponseBody
    public Object sdff(@PathVariable("service") String service, @PathVariable("method") String method, HttpServletRequest request) throws BridgeException {
        return new HttpProxy(bridge).process(service, method, request);
    }
}
