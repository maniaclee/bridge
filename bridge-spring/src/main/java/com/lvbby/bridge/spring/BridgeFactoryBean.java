package com.lvbby.bridge.spring;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.gateway.Bridge;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 */
public class BridgeFactoryBean implements FactoryBean<Bridge>, ApplicationListener {
    Bridge bridge = new Bridge();
    List<Class> annotations = Lists.newArrayList();

    {
        annotations.add(Service.class);
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
            for (Class annotation : annotations) {
                Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(annotation);
                for (Object bean : beansWithAnnotation.values()) {
                    // register service
                    bridge.addService(bean);
                }
            }
            bridge.init();
        }
    }

    public Bridge getObject() throws Exception {
        return bridge;
    }

    public Class<?> getObjectType() {
        return Bridge.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public List<Class> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Class> annotations) {
        this.annotations = annotations;
    }
}
