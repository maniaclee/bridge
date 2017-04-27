package com.lvbby.bridge.http;

import com.alibaba.fastjson.JSON;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.gateway.*;
import com.lvbby.bridge.http.filter.anno.HttpAttributeAnnotationValidateFilter;
import com.lvbby.bridge.http.filter.anno.HttpMethodFilter;
import com.lvbby.bridge.http.handler.HttpParameterParsingInitHandler;
import com.lvbby.bridge.http.handler.HttpSessionClearPostHandler;
import com.lvbby.bridge.http.handler.HttpSessionSavePostHandler;
import com.lvbby.bridge.http.parser.HttpApiRequestAttributeParser;
import com.lvbby.bridge.http.parser.HttpApiRequestParser;
import com.lvbby.bridge.http.parser.HttpApiRequestPathParser;
import com.lvbby.bridge.http.tool.HttpLoginHandler;

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

        /** add default filters */
        this.apiGateWay.addApiFilter(new HttpMethodFilter());
        this.apiGateWay.addApiFilter(new HttpAttributeAnnotationValidateFilter());

        /** param convert */
        this.apiGateWay.addInitHandler(new HttpParameterParsingInitHandler());
        /** inject */
        this.apiGateWay.addInitHandler(paramParsingContext -> {
            for (MethodParameter methodParameter : paramParsingContext.getApiMethod().getParamTypes()) {
                if (methodParameter.getType().isAssignableFrom(HttpServletRequest.class)) {
                    apiGateWay.paramsParser(paramParsingContext.getRequest()).addParameter(paramParsingContext, methodParameter, paramParsingContext.getRequest().getAttribute(EXT_HTTP_REQUEST));
                }
                if (methodParameter.getType().isAssignableFrom(HttpServletResponse.class)) {
                    apiGateWay.paramsParser(paramParsingContext.getRequest()).addParameter(paramParsingContext, methodParameter, paramParsingContext.getRequest().getAttribute(EXT_HTTP_RESPONSE));
                }
            }
        });
        /** add default post handlers */
        _addPostHandlerFirst(new HttpSessionSavePostHandler());
        _addPostHandlerFirst(new HttpSessionClearPostHandler());

        /** add login function */
        _addPreHandlerFirst(new HttpLoginHandler());
        _addPostHandlerFirst(new HttpLoginHandler());
    }

    public HttpBridge enableUrlPathParsing(String path) {
        this.httpApiRequestParser = HttpApiRequestPathParser.of(path);
        return this;
    }

    /***
     * add the system post handler to the first
     *
     * @param apiGateWayPostHandler
     */
    private void _addPostHandlerFirst(ApiGateWayPostHandler apiGateWayPostHandler) {
        ApiGateWay apiGateWay = getApiGateWay();
        if (apiGateWay instanceof AbstractApiGateWay) {
            ((AbstractApiGateWay) apiGateWay).getPostHandlers().add(0, apiGateWayPostHandler);
        }
    }

    private void _addPreHandlerFirst(ApiGateWayPreHandler apiGateWayPreHandler) {
        ApiGateWay apiGateWay = getApiGateWay();
        if (apiGateWay instanceof AbstractApiGateWay) {
            ((AbstractApiGateWay) apiGateWay).getPreHandlers().add(0, apiGateWayPreHandler);
        }
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

        req.addAttribute(EXT_HTTP_REQUEST, request).addAttribute(EXT_HTTP_RESPONSE, response);
        return apiGateWay.proxy(req);
    }


    public ApiGateWay getApiGateWay() {
        return apiGateWay;
    }

    public void setApiGateWay(ApiGateWay apiGateWay) {
        this.apiGateWay = apiGateWay;
    }

    public HttpApiRequestParser getHttpApiRequestParser() {
        return httpApiRequestParser;
    }

    public void setHttpApiRequestParser(HttpApiRequestParser httpApiRequestParser) {
        this.httpApiRequestParser = httpApiRequestParser;
    }
}
