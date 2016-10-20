package com.lvbby.bridge.test;

import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.gateway.Request;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

/**
 * Created by lipeng on 16/9/23.
 */
public class BridgeTest {

    private TestService testService = new TestService();

    @Test
    public void sdf() throws Exception {
        Bridge bridge = new Bridge()
                .addService(testService);
        bridge.init();
        Object proxy = bridge.proxy(new Request("TestService", "echo", Params.of(new Object[]{"shit", "hello"})));
        System.out.println(ReflectionToStringBuilder.toString(proxy));
    }

    @Test
    public void dfsdf() {
        System.out.println(Bridge.class.getSimpleName());
        System.out.println(Bridge.class.getName());
        System.out.println(Bridge.class.getCanonicalName());
    }

}
