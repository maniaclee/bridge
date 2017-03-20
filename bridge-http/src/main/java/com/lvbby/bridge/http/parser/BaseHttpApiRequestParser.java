package com.lvbby.bridge.http.parser;

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

        //handle parameters
        if (ParamFormat.MAP.getValue().equals(re.getParamType()) || ParamFormat.MAP_PRECISE.getValue().equals(re.getParamType())) {
            try {
                re.setParam(extractHttpParameters(request));
            } catch (URISyntaxException e) {
                throw new BridgeRoutingException("bad url format ", e);
            }
        } else {
            re.setParam(request.getParameter(paramAttribute));
        }
        return re;
    }

    private Map<String, Object> extractHttpParameters(HttpServletRequest request) throws URISyntaxException {
        Map<String, Object> re = Maps.newHashMap();
        //from url
        if ("get".equalsIgnoreCase(request.getMethod())) {
            List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.forName("UTF-8"));
//            List<NameValuePair> params = URLEncodedUtils.parse(new URI(request.getRequestURI()), "UTF-8");
            for (NameValuePair param : params) {
                if (!isSystemParamter(param.getName()))
                    re.put(param.getName(), param.getValue());
            }
            return re;
        }
        //from attribute
        for (Enumeration ps = request.getAttributeNames(); ps.hasMoreElements(); ) {
            String key = ps.nextElement().toString().trim();
            if (!isSystemParamter(key))
                re.put(key, request.getAttribute(key));
        }
        return re;
    }

    private boolean isSystemParamter(String key) {
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
