package com.lvbby.bridge.http.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.util.Validate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by lipeng on 2018/6/16.
 */
public class HttpPayloadRequestParser implements HttpApiRequestParser {
    String attribute_request = "request";
    String attribute_method = "method";
    String attribute_service = "service";

    private String paramTypeAttribute = "_paramType";
    private String paramAttribute     = "_param";
    private String serviceAttribute = "_service";
    private String methodAttribute = "_method";

    @Override
    public Request parse(HttpServletRequest request) throws BridgeRoutingException {
        try {
            if ("get".equalsIgnoreCase(request.getMethod())) {
                return parseGet(request, s -> !isSystemParameter(s));
            }
            return parsePost(request);
        } catch (URISyntaxException e) {
            throw new BridgeRoutingException("bad url format", e);
        }
    }

    public Request parsePost(HttpServletRequest request) throws BridgeRoutingException {
        try {
            String s = IOUtils.toString(request.getInputStream());
            JSONObject map = JSON.parseObject(s);
            Object param = map.get(attribute_request);
            String service = (String) map.get(attribute_service);
            String method = (String) map.get(attribute_method);
            Validate.notBlank(service, "no service given");
            Validate.notBlank(method, "no method given");

            Request req = new Request().buildParam(param).buildType(ParamFormat.JsonObject);
            req.setService(service);
            req.setMethod(method);
            return req;
        } catch (IOException e) {
            throw new BridgeRoutingException("failed parsing request", e);
        }
    }
    /***
     * extract parameters
     * @param request
     * @param keyFilter
     * @return
     * @throws URISyntaxException
     */

    //from url
    private Request parseGet(HttpServletRequest request, Predicate<String> keyFilter) throws URISyntaxException {
        Request req = new Request();
        Map<String, Object> re = Maps.newHashMap();
        String queryString = request.getQueryString();
        if (!StringUtils.isBlank(queryString)) {
            List<NameValuePair> params = URLEncodedUtils.parse(queryString, Charset.forName("UTF-8"));
            if (params != null) {
                params.stream().filter(nameValuePair -> keyFilter == null || keyFilter.test(nameValuePair.getName()))
                        .forEach(nameValuePair -> re.putIfAbsent(nameValuePair.getName(), nameValuePair.getValue()));
            }
        }
        req.setService(request.getParameter(serviceAttribute));
        req.setMethod(request.getParameter(methodAttribute));
        return req.buildParam(re).buildType(ParamFormat.Json);
    }

    private boolean isSystemParameter(String key) {
        return key.startsWith("_");
    }

    public String getParamAttribute() {
        return paramAttribute;
    }

    public void setParamAttribute(String paramAttribute) {
        this.paramAttribute = paramAttribute;
    }

    public String getParamTypeAttribute() {
        return paramTypeAttribute;
    }

    public void setParamTypeAttribute(String paramTypeAttribute) {
        this.paramTypeAttribute = paramTypeAttribute;
    }


}
