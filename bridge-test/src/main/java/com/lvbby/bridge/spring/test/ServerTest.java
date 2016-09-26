package com.lvbby.bridge.spring.test;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.http.HttpServer;
import com.lvbby.bridge.spring.test.service.TestServiceImpl;
import org.junit.Test;

/**
 * Created by lipeng on 16/9/26.
 */
public class ServerTest {

    @Test
    public void run() throws Exception {
        HttpServer httpServer = new HttpServer(Lists.newArrayList(new TestServiceImpl()));
        httpServer.start();
    }

    public static void main(String[] args) throws Exception {
        new ServerTest().run();
    }
}
