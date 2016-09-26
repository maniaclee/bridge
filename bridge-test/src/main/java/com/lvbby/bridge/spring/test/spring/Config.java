package com.lvbby.bridge.spring.test.spring;

import com.lvbby.bridge.spring.BridgeFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by peng on 16/9/24.
 */
@Configuration
@ComponentScan("com.lvbby")
public class Config {

    @Bean
    public BridgeFactoryBean bridgeFactoryBean() {
        BridgeFactoryBean bridgeFactoryBean = new BridgeFactoryBean();
        return bridgeFactoryBean;
    }
}
