package com.lvbby.bridge.test;

import com.google.common.collect.Maps;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.gateway.Request;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by lipeng on 16/9/23.
 */
public class BridgeTest {

    private TestService testService = new TestService();

    @Test
    public void mapEmpty() throws Exception {
        ApiGateWay bridge = new Bridge().addService(testService);

        HashMap<Object, Object> param = Maps.newHashMap();

        Object proxy = null;
        Exception ex=null;
        try {
            proxy = bridge.proxy(new Request("TestService", "echo", param).buildType(ParamFormat.Map));

        } catch (Exception e) {
            ex=e;
        }
        Validate.notNull(ex);
    }

    @Test
    public void map() throws Exception {
        ApiGateWay bridge = new Bridge().addService(testService);
        Request request = new Request("TestService", "run")
                .buildType(ParamFormat.Map)
                .buildParam(new HashMap() {
                    {
                        put("key", "key");
                        put("value", "fuck");
                        put("type", 1);
                    }
                });
        Object proxy = bridge.proxy(request);
        System.out.println(ReflectionToStringBuilder.toString(proxy));
    }
    @Test
    public void mapObject() throws Exception {
        ApiGateWay bridge = new Bridge().addService(testService);
        Request request = new Request("TestService", "signle")
                .buildType(ParamFormat.Json)
                .buildParam(new HashMap() {
                    {
                        put("request", new HashMap() {
                            {
                                put("name", "key");
                                put("request",new HashMap() {
                                    {
                                        put("service", "service");
                                        put("method", "method");
                                    }
                                });
                            }
                        });
                    }
                });
        Object proxy = bridge.proxy(request);
        System.out.println(ReflectionToStringBuilder.toString(proxy));
    }

    @Test
    public void array() throws Exception {
        ApiGateWay bridge = new Bridge().addService(testService);
        Request request = new Request("TestService", "run")
                .buildType(ParamFormat.Array)
                .buildParam(new Object[]{"fuck", "you", 333});
        Object proxy = bridge.proxy(request);
        System.out.println(ReflectionToStringBuilder.toString(proxy));
    }

    @Test
    public void match() {
        Class clz = int.class;
        Integer test = 1;

        System.out.println(clz.getClass().isAssignableFrom(test.getClass()));
    }

}
