package test;

import com.google.common.collect.Lists;
import com.lvbby.bridge.http.HttpBridgeServer;
import com.lvbby.bridge.http.gateway.HttpServiceProxy;
import org.junit.Before;
import org.junit.Test;
import service.TestService;
import service.TestServiceImpl;

/**
 * Created by lipeng on 2017/4/27.
 */
public class HttpTest {

    HttpBridgeServer server;
    private int port = 9003;

    @Before
    public void init() throws Exception {
        server = HttpBridgeServer.of(Lists.newArrayList(new TestServiceImpl()));
        server.getServer().setPort(port);
        server.start();
    }

    @Test
    public void sdf() {
        HttpServiceProxy<TestService> proxy = new HttpServiceProxy(TestService.class);
        proxy.setHost("localhost");
        proxy.setPath("api");
        proxy.setPort(port);
        TestService testService = ((HttpServiceProxy<TestService>) proxy).proxy();
        testService.echo("sdf");
        System.out.println(testService.handle("shit", "f"));
    }
}
