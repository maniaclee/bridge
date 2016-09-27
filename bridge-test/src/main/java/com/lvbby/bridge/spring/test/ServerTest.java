package com.lvbby.bridge.spring.test;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.http.HttpBridgeServer;
import com.lvbby.bridge.spring.test.service.TestServiceImpl;

/**
 * Created by lipeng on 16/9/26.
 */
public class ServerTest {

    public static void main(String[] args) throws Exception {
        new HttpBridgeServer(Lists.newArrayList(new TestServiceImpl())).start();
    }
}
