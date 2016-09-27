package com.lvbby.bridge.spring.test;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.http.HttpBridgeServer;
import com.lvbby.bridge.spring.test.service.TestServiceImpl;
import org.junit.Test;

/**
 * Created by lipeng on 16/9/26.
 */
public class ServerTest {

    @Test
    public void run() throws Exception {
        HttpBridgeServer httpBridgeServer = new HttpBridgeServer(Lists.newArrayList(new TestServiceImpl()));
        httpBridgeServer.start();
    }

    public static void main(String[] args) throws Exception {
        new ServerTest().run();
    }
}
