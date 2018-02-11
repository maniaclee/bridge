/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.lvbby.bridge.test;

import com.lvbby.bridge.gateway.Request;

/**
 *
 * @author dushang.lp
 * @version $Id: BridgeParamTest.java, v 0.1 2018年02月11日 下午3:03 dushang.lp Exp $
 */
public class BridgeParamTest {
    private String name;
    private Request request;

    /**
     * Getter method for property   request.
     *
     * @return property value of request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Setter method for property   request .
     *
     * @param request  value to be assigned to property request
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Getter method for property   name.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property   name .
     *
     * @param name  value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }
}