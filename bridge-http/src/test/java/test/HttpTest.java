package test;

import com.google.common.collect.Lists;
import com.lvbby.bridge.http.HttpBridgeServer;
import com.lvbby.bridge.http.gateway.HttpServiceProxy;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Before;
import org.junit.Test;
import service.TestService;
import service.TestServiceImpl;

import java.nio.charset.Charset;
import java.util.List;

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
        HttpServiceProxy<TestService> proxy = new HttpServiceProxy();
        proxy.setHost("localhost");
        proxy.setPath("api");
        proxy.setPort(port);
        TestService testService = ((HttpServiceProxy<TestService>) proxy).proxy();
        testService.echo("sdf");
        System.out.println(testService.handle("shit", "f"));
    }

    @Test
    public void arrayUrl() throws Exception {
        String queryString = "ids=1&ids=2";
        List<NameValuePair> params = URLEncodedUtils.parse(queryString, Charset.forName("UTF-8"));
        for (NameValuePair param : params) {
            System.out.println(param.getName() + " " + param.getValue());
        }
    }
}
