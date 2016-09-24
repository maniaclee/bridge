package com.lvbby.bridge.spring.test.service;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.wrapper.Context;
import com.lvbby.bridge.api.wrapper.Params;
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
        bridge.proxy(new Context("TestService", "echo", Params.of(new Object[]{"shit"})));
    }
}
