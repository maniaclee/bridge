package com.lvbby.bridge.http.parser;

import com.google.common.collect.ArrayListMultimap;
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
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by lipeng on 16/10/20.
 * extract param && paramType , let subclass to parse the service && method
 */
public class BaseHttpApiRequestParser implements HttpApiRequestParser {

    private String paramTypeAttribute = "_paramType";
    private String paramAttribute = "_param";

    @Override
    public Request parse(HttpServletRequest request) throws BridgeRoutingException {
        Request re = new Request();
        //handle paramType
        if (StringUtils.isNotBlank(request.getParameter(paramTypeAttribute)))
            re.setParamType(request.getParameter(paramTypeAttribute));
        else
            re.setParamType(ParamFormat.Map.getValue());

        //handle parameters
        try {
            switch (ParamFormat.parse(re.getParamType())) {
                case Map:
                    re.setParam(extractHttpParameters(request, s -> !isSystemParameter(s)));
                    break;
                case Array:
                    re.setParam(extractHttpParameters(request, null).get(paramAttribute));
                    break;
            }
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
    private Map<String, Object> extractHttpParameters(HttpServletRequest request, Predicate<String> keyFilter) throws URISyntaxException {
        Map<String, Object> re = Maps.newHashMap();
        //from url
        if ("get".equalsIgnoreCase(request.getMethod())) {
            String queryString = request.getQueryString();
            if (!StringUtils.isBlank(queryString)) {
                ArrayListMultimap<String, String> map = ArrayListMultimap.create();
                List<NameValuePair> params = URLEncodedUtils.parse(queryString, Charset.forName("UTF-8"));
                if (params != null) {
                    params.forEach(nameValuePair -> map.put(nameValuePair.getName(), nameValuePair.getValue()));
                }
                for (String key : map.keySet()) {
                    List<String> values = map.get(key);
                    Object value = null;
                    if (values != null && !values.isEmpty())
                        value = values.size() == 1 ? values.get(0) : values;
                    if (keyFilter == null || keyFilter.test(key))
                        re.put(key, value);
                }
            }
            return re;
        }
        //from parameters
        for (Enumeration ps = request.getParameterNames(); ps != null && ps.hasMoreElements(); ) {
            String key = ps.nextElement().toString().trim();
            if (keyFilter == null || keyFilter.test(key))
                re.put(key, request.getParameter(key));
        }
        return re;
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
