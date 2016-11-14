package com.lvbby.bridge.spring.test;

import com.lvbby.bridge.http.gateway.HttpServiceProxy;
import com.lvbby.bridge.spring.test.service.TestService;
import org.junit.Test;

/**
 * Created by lipeng on 16/11/11.
 */
public class ClientTest {

    @Test
    public void sdf() {
        HttpServiceProxy<TestService> proxy = new HttpServiceProxy(String.class);
        proxy.setHost("localhost");
        proxy.setPath("api");
        proxy.setPort(7000);
        TestService testService = ((HttpServiceProxy<TestService>) proxy).proxy();
        testService.echo("sdf");
        System.out.println(testService.handle("shit", "f"));
    }

    @Test
    public void sdfsdf(){
        System.out.println(new HttpServiceProxy<String>().getType());
    }
}
