package com.lvbby.bridge.spring;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.gateway.ApiGateWay;
import com.lvbby.bridge.api.gateway.Bridge;
import com.lvbby.bridge.api.gateway.ApiService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.Map;

/**
 * Created by peng on 16/9/22.
 */
public class BridgeFactoryBean extends Bridge implements FactoryBean<ApiGateWay>, ApplicationListener {
    List<Class> annotations = Lists.newArrayList();
    List<Class> beanClasses = Lists.newLinkedList();
    List beanObjects = Lists.newLinkedList();

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            process(((ContextRefreshedEvent) event).getApplicationContext());
        }
    }

    public BridgeFactoryBean addBean(Class beanClz) {
        beanClasses.add(beanClz);
        return this;
    }

    public BridgeFactoryBean addBeanObject(Object beanClz) {
        beanObjects.add(beanClz);
        return this;
    }

    public void process(ApplicationContext applicationContext) {
        for (Class annotation : annotations) {
            Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(annotation);
            for (Object bean : beansWithAnnotation.values()) {
                // register service
                addService(bean);
            }
        }
        for (Class beanClass : beanClasses) {
            /** spring bean's interface will be disturbed by spring's aop & proxy */
            addApiService(ApiService.of(applicationContext.getBean(beanClass), getServiceNameExtractor().getServiceName(beanClass)));
        }
        for (Object beanObject : beanObjects) {
            addService(beanObject);
        }
        init();
    }


    public Bridge getObject() throws Exception {
        return this;
    }

    public Class<?> getObjectType() {
        return ApiGateWay.class;
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
