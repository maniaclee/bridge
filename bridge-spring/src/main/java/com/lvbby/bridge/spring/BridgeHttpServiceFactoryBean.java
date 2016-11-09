package com.lvbby.bridge.spring;

import com.lvbby.bridge.http.gateway.HttpServiceProxy;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by lipeng on 16/11/9.
 */
public class BridgeHttpServiceFactoryBean<T> extends HttpServiceProxy<T> implements FactoryBean<T> {

    public BridgeHttpServiceFactoryBean(Class<T> clz) {
        super(clz);
    }

    @Override
    public T getObject() throws Exception {
        return proxy();
    }

    @Override
    public Class<?> getObjectType() {
        return getClz();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
