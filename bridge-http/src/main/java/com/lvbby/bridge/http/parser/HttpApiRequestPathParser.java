package com.lvbby.bridge.http.parser;

import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.Request;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.utils.URIBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lipeng on 16/10/29.
 * 先从参数里找service/method,然后从path里找，rootPath/service/method
 */
public class HttpApiRequestPathParser extends HttpApiRequestAttributeParser {
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
        if (StringUtils.isBlank(re.getService())) {
            try {
                Pair<String, String> ss = parsePath(request.getRequestURI());
                if (ss == null)
                    throw new BridgeRoutingException(String.format("Invalid url[%s] , show be /{rootPath}/{service}/{method}", request.getRequestURI()));
                re.setService(ss.getLeft());
                re.setMethod(ss.getRight());
            } catch (Exception e) {
                throw new BridgeRoutingException(String.format("Can't find service and method from url[%s]", request.getRequestURI()), e);
            }
        }
        return re;
    }

    private String formatUrl(String url) {
        url = StringUtils.trimToEmpty(url).replaceAll("\"", "");
        if (!url.startsWith("/"))
            url = "/" + url;
        return url;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    private Pair<String, String> parsePath(String url) throws Exception {
        URIBuilder build = new URIBuilder(url);
        String path = build.getPath();
        String prefix = rootPath.replaceAll("\\*|^|\\?", "").replaceAll("/+","/");
        if (!rootPath.startsWith("/"))
            prefix = "/" + prefix;
        Matcher matcher = Pattern.compile(prefix + "/?(?<service>[^/]+)/(?<method>[^/]+)/?$").matcher(path);
        if (matcher.find()) {
            String service = matcher.group("service");
            String method = matcher.group("method");
            if (StringUtils.isNotBlank(service) && StringUtils.isNotBlank(method))
                return Pair.of(service, method);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        URIBuilder build = new URIBuilder("http://localhost:799/api/root/servicea/methodxx?param=3");
        System.out.println(build.getPath());
        System.out.println(of("api/root/**/").parsePath(build.toString()));
        System.out.println(of("/api/root**/").parsePath(build.toString()));
    }
}
