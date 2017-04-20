package com.lvbby.bridge.spring;

import com.lvbby.bridge.exception.BridgeRunTimeException;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.http.HttpBridge;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by peng on 2016/9/27.
 */

public class BridgeController implements ApplicationListener<ContextRefreshedEvent> {

    private HttpBridge httpBridge;

    @RequestMapping
    @ResponseBody
    public Object proxy(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return httpBridge.process(request, response);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        try {
            HttpBridge bean = applicationContext.getBean(HttpBridge.class);
            if (bean != null) {
                this.httpBridge = bean;
                return;
            }
        } catch (BeansException e) {
        }
        ApiGateWay bridge = applicationContext.getBean(ApiGateWay.class);
        if (bridge == null)
            throw new BridgeRunTimeException("no bridge found in spring context");

        /** 支持从url解析service和method */
        RequestMapping annotation = getClass().getAnnotation(RequestMapping.class);
        if (annotation == null)
            throw new IllegalArgumentException(String.format("controller class[%s] is a proxy class, don't use Configuration etc annotation in controller." , getClass().getName()));
        httpBridge = HttpBridge.of(bridge).enableUrlPathParsing(annotation.value()[0]);
    }
}
