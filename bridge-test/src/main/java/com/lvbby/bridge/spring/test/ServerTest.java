package com.lvbby.bridge.spring.test;

import com.google.common.collect.Lists;
import com.lvbby.bridge.http.HttpBridgeServer;
import com.lvbby.bridge.spring.test.service.TestServiceImpl;

/**
 * Created by lipeng on 16/9/26.
 */
public class ServerTest {

    public static void main(String[] args) throws Exception {
        HttpBridgeServer.of(Lists.newArrayList(new TestServiceImpl())).start();
//        AdminServer.of(new HttpBridge(new Bridge().addService(new TestServiceImpl()))).start();
    }

}
