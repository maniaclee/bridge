package com.lvbby.bridge.spring.test;

import com.lvbby.bridge.exception.BridgeException;
import com.lvbby.bridge.gateway.Bridge;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.spring.test.spring.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by peng on 16/9/24.
 */
@SpringBootApplication
@ContextConfiguration(classes = {Config.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BridgeSpringTest {

    @Autowired
    private Bridge bridge;

    @Test
    public void sdfsdf() throws BridgeException {
        bridge.proxy(new Request("TestService", "echo", Params.of(new Object[]{"shit"})));
    }
}
