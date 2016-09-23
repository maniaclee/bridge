package com.lvbby.bridge.api.test;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.wrapper.ApiService;
import com.lvbby.bridge.api.wrapper.Context;
import com.lvbby.bridge.api.wrapper.Param;
import com.lvbby.bridge.api.wrapper.Params;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

/**
 * Created by lipeng on 16/9/23.
 */
public class BridgeTest {

    private TestService testService = new TestService();

    @Test
    public void sdf() throws BridgeException {
        Bridge bridge = new Bridge();
        bridge.setServices(Lists.newArrayList(ApiService.of(testService)));
        bridge.init();
        Context context = new Context();
        context.setServiceName("TestService");
        context.setMethod("echo");
        context.setParam(new Params(new Param[]{new Param("shit")}));
        Object proxy = bridge.proxy(context);
        System.out.println(ReflectionToStringBuilder.toString(proxy));
    }
}
