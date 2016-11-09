package com.lvbby.bridge.http.parser;

import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.Request;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lipeng on 16/10/29.
 */
public class HttpApiRequestPathParser extends BaseHttpApiRequestParser {
    private String rootPath;

    private HttpApiRequestPathParser(String rootPath) {
        this.rootPath = formatUrl(rootPath);
    }

    public static HttpApiRequestPathParser of(String rootPath) {
        return new HttpApiRequestPathParser(rootPath);
    }

    @Override
    public Request parse(HttpServletRequest request) throws BridgeRoutingException {
        Request re = super.parse(request);
        String url = formatUrl(request.getRequestURI());
        if (!url.startsWith(rootPath))
            throw new BridgeRoutingException(String.format("Can't find service and method from url[%s]", request.getRequestURI()));
        String[] split = trimPath(url.substring(rootPath.length())).split("/");
        if (split.length != 2)
            throw new BridgeRoutingException(String.format("Invalid url[%s] , show be /{rootPath}/{service}/{method}", request.getRequestURI()));
        re.setService(split[0]);
        re.setMethod(split[1]);
        return re;
    }

    private String formatUrl(String url) {
        url = StringUtils.trimToEmpty(url).replaceAll("\"", "");
        if (!url.startsWith("/"))
            url = "/" + url;
        return url;
    }

    private String trimPath(String url) {
        if (url.startsWith("/"))
            url = url.substring(1);
        if (url.endsWith("/"))
            url = url.substring(0, url.length() - 1);
        return url;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
