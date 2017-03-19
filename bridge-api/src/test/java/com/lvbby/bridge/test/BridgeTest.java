package com.lvbby.bridge.test;

import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.gateway.Request;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by lipeng on 16/9/23.
 */
public class BridgeTest {

    private TestService testService = new TestService();

    @Test
    public void sdf() throws Exception {
        ApiGateWay bridge = new Bridge().addService(testService);
        Object proxy = bridge.proxy(new Request("TestService", "echo", "{s:'sdf'}"));
        System.out.println(ReflectionToStringBuilder.toString(proxy));
    }

    @Test
    public void map() throws Exception {
        ApiGateWay bridge = new Bridge().addService(testService);
        Request request = new Request("TestService", "run", new HashMap() {
            {
                put("key", "key");
                put("value", "fuck");
            }
        });
        request.setParamType(ParamFormat.MAP.getValue());
//        request.setParamType(ParamFormat.MAP_BEST_MATCH.getValue());
        Object proxy = bridge.proxy(request);
        System.out.println(ReflectionToStringBuilder.toString(proxy));
    }

}
