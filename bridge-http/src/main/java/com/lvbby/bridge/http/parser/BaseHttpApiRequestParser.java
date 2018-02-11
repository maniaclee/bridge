package com.lvbby.bridge.http.parser;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.Request;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by lipeng on 16/10/20.
 * extract param && paramType , let subclass to parse the service && method
 */
public class BaseHttpApiRequestParser implements HttpApiRequestParser {

    private String paramTypeAttribute = "_paramType";
    private String paramAttribute     = "_param";

    @Override
    public Request parse(HttpServletRequest request) throws BridgeRoutingException {
        Request re = new Request();
        //handle paramType
        if (StringUtils.isNotBlank(request.getParameter(paramTypeAttribute)))
            re.setParamType(request.getParameter(paramTypeAttribute));
        else
            re.setParamType(ParamFormat.Json.getValue());

        //handle parameters
        try {
            re.setParam(extractHttpParameters(request, s -> !isSystemParameter(s)));
        } catch (URISyntaxException e) {
            throw new BridgeRoutingException("bad url format", e);
        }
        return re;
    }

    /***
     * extract parameters
     * @param request
     * @param keyFilter
     * @return
     * @throws URISyntaxException
     */

    //from url
    private Map<String, Object> extractHttpParameters(HttpServletRequest request, Predicate<String> keyFilter) throws URISyntaxException {
        Map<String, Object> re = Maps.newHashMap();
        if ("get".equalsIgnoreCase(request.getMethod())) {
            String queryString = request.getQueryString();
            if (!StringUtils.isBlank(queryString)) {
                List<NameValuePair> params = URLEncodedUtils.parse(queryString, Charset.forName("UTF-8"));
                if (params != null) {
                    params.stream().filter(nameValuePair -> keyFilter == null || keyFilter.test(nameValuePair.getName()))
                        .filter(nameValuePair -> nameValuePair.getValue() == null)
                        .forEach(nameValuePair -> re.putIfAbsent(nameValuePair.getName(), nameValuePair.getValue()));
                }
            }
            return re;
        }
        //from parameters
        for (Enumeration ps = request.getParameterNames(); ps != null && ps.hasMoreElements();) {
            String key = ps.nextElement().toString().trim();
            if (keyFilter == null || keyFilter.test(key))
                re.put(key, request.getParameter(key));
        }
        return parseBridgeParameters(re);
    }

    //a.b.c=value 形式的参数转为json形式的map
    private static Map<String, Object> parseBridgeParameters(Map<String, Object> src){
        Map re = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : src.entrySet()) {
            String[] split = entry.getKey().split("\\.");
            Map target = re;
            for (int i = 0; i < split.length-1; i++) {
                String key = split[i];
                if(target.get(key) == null){
                    Map<String, Object> value = Maps.newHashMap();
                    target.put(key, value);
                    target=value;
                }else {
                    target = (Map) target.get(key);
                }
            }
            target.put(split[split.length-1],entry.getValue());
        }
        return re;
    }

    public static void main(String[] args) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("a.b.c","cvalue");
        map.put("a.d","d");
        map.put("F","f");
        System.out.println(JSON.toJSONString(parseBridgeParameters(map),true));
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
