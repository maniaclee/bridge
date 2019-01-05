package com.lvbby.bridge.spring;

import com.lvbby.bridge.http.gateway.HttpServiceProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactoryBean;

import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/11/9.
 */
public class BridgeHttpServiceFactoryBean<T> extends HttpServiceProxy<T> {

    public static <R> R getInstance(Class<R> clz,String url) {
        BridgeHttpServiceFactoryBean<R> re = new BridgeHttpServiceFactoryBean<>(clz);
        re.url(url);
        return re.proxy();
    }

    public BridgeHttpServiceFactoryBean(Class<T> clz) {
        super(clz);
    }

    @Override public T proxy() {
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setTargetClass(getClz());
        pfb.addAdvice((MethodInterceptor) methodInvocation -> {
            Method method = methodInvocation.getMethod();
            Object[] arguments = methodInvocation.getArguments();
            return BridgeHttpServiceFactoryBean.this.invoke(method, arguments);
        });
        return (T) pfb.getObject();
    }
}
