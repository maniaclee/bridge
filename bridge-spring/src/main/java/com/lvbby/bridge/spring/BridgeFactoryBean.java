package com.lvbby.bridge.spring;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.Bridge;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by peng on 16/9/22.
 */
public class BridgeFactoryBean extends Bridge implements FactoryBean<ApiGateWay>, ApplicationListener {
    List<Class> annotations = Lists.newArrayList();
    List<Class> beanClasses = Lists.newLinkedList();
    List beanObjects = Lists.newLinkedList();
    private ApplicationContext applicationContext;

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            this.applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
            process(applicationContext);
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
        //处理代理
        getAllApiServices().forEach(apiService -> processApiService(apiService));
    }

    public void processApiService(ApiService apiService) {
        try {
            Object service = apiService.getService();
            //1. 如果method上有Interceptor，添加"只在该方法上有效"的Interceptor
            List<MethodInterceptor> re = Lists.newLinkedList();
            Arrays.stream(service.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Interceptor.class)).forEach(method -> {
                Interceptor in = method.getAnnotation(Interceptor.class);
                parseInterceptors(in).forEach(methodInterceptor -> re.add(methodInvocation -> {
                    //只在该方法上其作用
                    if (methodInvocation.getMethod().equals(method)) {
                        return methodInterceptor.invoke(methodInvocation);
                    }
                    return methodInvocation.proceed();
                }));
            });
            //2. 如果类上面Interceptor，直接添加，对所有方法都有效
            Interceptor annotation = service.getClass().getAnnotation(Interceptor.class);
            if (annotation != null) {
                re.addAll(parseInterceptors(annotation));
            }
            //3. 生成proxy，替换以前的service object
            if (!re.isEmpty()) {
                ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
                proxyFactoryBean.setProxyInterfaces(service.getClass().getInterfaces());
                proxyFactoryBean.setTarget(service);
                proxyFactoryBean.setProxyTargetClass(true);
                re.forEach(methodInterceptor -> proxyFactoryBean.addAdvice(methodInterceptor));
                apiService.setService(proxyFactoryBean.getObject());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<MethodInterceptor> parseInterceptors(Interceptor annotation) {
        Class<? extends MethodInterceptor>[] clz = annotation.clz();
        if (clz != null) {
            return Arrays.stream(clz).map(aClass -> {
                try {
                    return aClass.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        }
        if (annotation.bean() != null) {
            return Arrays.stream(annotation.bean()).map(s -> applicationContext.getBean(s, MethodInterceptor.class)).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("interceptors not found");
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
