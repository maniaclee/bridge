package com.lvbby.bridge.http;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.gateway.InjectProcessor;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.http.handler.HttpMethodFilter;
import com.lvbby.bridge.http.parser.HttpApiRequestAttributeParser;
import com.lvbby.bridge.http.parser.HttpApiRequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by peng on 16/9/24.
 * usage above the ApiGateWay
 */
public class HttpBridge {
    private ApiGateWay apiGateWay;
    private HttpApiRequestParser httpApiRequestParser = new HttpApiRequestAttributeParser();
    public static final String EXT_HTTP_REQUEST = "EXT_HTTP_REQUEST";
    public static final String EXT_HTTP_RESPONSE = "EXT_HTTP_RESPONSE";

    public HttpBridge(ApiGateWay apiGateWay) {
        this.apiGateWay = apiGateWay;

        this.apiGateWay.addApiFilter(new HttpMethodFilter());
    }

    public static HttpBridge of(ApiGateWay apiGateWay) {
        return new HttpBridge(apiGateWay);
    }


    /***
     * write the response back to the response as json
     *
     * @param request
     * @param response
     */
    public void processBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Object re = process(request, response);
            if (re == null)
                return;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(re));
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object process(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Request req = httpApiRequestParser.parse(request);
        /** set context */
        HttpContextHolder.setServlet(request, response);

        /** inject HttpServletRequest && HttpServletResponse */
        InjectProcessor.setInjectValue(Lists.newArrayList(request, response));

        try {
            req.addAttribute(EXT_HTTP_REQUEST, request).addAttribute(EXT_HTTP_RESPONSE, response);
            return apiGateWay.proxy(req);
        } finally {
            /** clear context */
            HttpContextHolder.clear();
        }
    }

    public ApiGateWay getApiGateWay() {
        return apiGateWay;
    }
}
