package com.lvbby.bridge.spring.test;

import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.http.HttpBridge;
import com.lvbby.bridge.http.admin.AdminServer;
import com.lvbby.bridge.spring.test.service.TestServiceImpl;

/**
 * Created by lipeng on 16/9/26.
 */
public class ServerTest {

    public static void main(String[] args) throws Exception {
        //        new HttpBridgeServer(Lists.newArrayList(new TestServiceImpl())).start();
        AdminServer.of(new HttpBridge(new Bridge().addService(new TestServiceImpl()))).start();
    }

}
