package com.lvbby.bridge.spring.test.spring;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.http.HttpBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("api")
    @ResponseBody
    public Object sdff(HttpServletRequest request) throws BridgeException {
        return new HttpBridge(bridge).process(request);
    }
}
