package com.lvbby.bridge.api.test;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.gateway.Context;
import com.lvbby.bridge.api.gateway.Params;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

/**
 * Created by lipeng on 16/9/23.
 */
public class BridgeTest {

    private TestService testService = new TestService();

    @Test
    public void sdf() throws BridgeException {
        Bridge bridge = new Bridge()
                .addService(testService);
        bridge.init();
        Object proxy = bridge.proxy(new Context("TestService", "echo", Params.of(new Object[]{"shit", "hello"})));
        System.out.println(ReflectionToStringBuilder.toString(proxy));
    }

    @Test
    public void dfsdf() {
        System.out.println(Bridge.class.getSimpleName());
        System.out.println(Bridge.class.getName());
        System.out.println(Bridge.class.getCanonicalName());
    }

}
