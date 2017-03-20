package com.lvbby.bridge.spring.test;

import com.google.common.collect.Lists;
import com.lvbby.bridge.http.HttpBridgeServer;
import com.lvbby.bridge.spring.test.service.TestServiceImpl;

/**
 * Created by lipeng on 16/9/26.
 */
public class ServerTest {

    /***
     *
     * http://localhost:9000/api?_service=TestService&_method=inject&_paramType=map&s=what&shit=thehell
     * http://localhost:9000/api?_service=TestService&_method=inject&_paramType=json&_param={'s':'what','b':'the hell'}
     * http://localhost:9000/api?_service=TestService&_method=echo&_paramType=map
     */
    public static void main(String[] args) throws Exception {
        HttpBridgeServer server = HttpBridgeServer.of(Lists.newArrayList(new TestServiceImpl()));
        server.getServer().setPort(9000);
        server.start();
        //        AdminServer.of(new HttpBridge(new Bridge().addService(new TestServiceImpl()))).start();
    }

}
