package com.lvbby.bridge.spring;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;

/**
 * Created by lipeng on 2018/3/17.
 */
public class SpringUtils {

    public static Class getBeanClass(Object bean){
        if(bean==null)
            return null;
        if(bean instanceof Class){
            return (Class) bean;
        }
        if(!AopUtils.isAopProxy(bean))
            return bean.getClass();
        //return AopUtils.getTargetClass(bean);
        return AopProxyUtils.ultimateTargetClass(bean);
    }
}
