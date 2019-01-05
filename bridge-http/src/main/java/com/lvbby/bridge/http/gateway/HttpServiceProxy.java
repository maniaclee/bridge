package com.lvbby.bridge.http.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.param.extracotr.DefaultParameterNameExtractor;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * Created by lipeng on 16/11/9.
 */
public class HttpServiceProxy<T> {

    private String host = "localhost";
    private int port = 80;
    private String path = "/";
    private String url;
    private String scheme = "http";
    private OkHttpClient client = new OkHttpClient();
    private Map<String, String> httpMethodTypes = Maps.newHashMap();
    private Class<T> clz;
    private String methodType = "GET";
    private static List<String> excludeMethods = Lists.newArrayList();

    static {
        for (Method method : Object.class.getDeclaredMethods()) excludeMethods.add(method.getName());
    }


    public static <R> R of(Class<R> clz,String url) throws Exception {
        HttpServiceProxy<R> proxy = new HttpServiceProxy<>(clz);
        proxy.url(url);
        return proxy.proxy();
    }

    public void url(String url) {
        this.url = url;
    }

    public HttpServiceProxy(Class<T> clz) {
        this.clz = clz;
    }

    public HttpServiceProxy() {
    }

    public Class<T> getType() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
    }

    private void init() {
        Validate.notNull(host);
        Validate.notNull(path);
        for (Method method : clz.getDeclaredMethods()) {
            method.setAccessible(true);
            String methodName = getMethodName(method);
            if (isValidMethod(method) && !httpMethodTypes.containsKey(methodName)) {
                httpMethodTypes.put(methodName, methodType);
            }
        }
        url = new HttpUrl.Builder().scheme(scheme).addPathSegment(path).port(port).host(this.host).build().url().toString();
    }

    private boolean isValidMethod(Method method) {
        return Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers());
    }

    public void setHttpMethodType(String method, String methodType) {
        httpMethodTypes.put(getMethodName(findMethod(method)), methodType);
    }

    private Method findMethod(String methodName) {
        List<Method> re = Lists.newArrayList();
        for (Method m : clz.getDeclaredMethods()) {
            if (isValidMethod(m) && m.getName().equals(methodName))
                re.add(m);
        }
        if (re.size() == 0)
            throw new IllegalArgumentException(String.format("%s.%s not found", clz.getName(), methodName));
        if (re.size() > 1)
            throw new IllegalArgumentException(String.format("%s.%s found multiple methods", clz.getName(), methodName));
        return re.get(0);
    }

    private String getMethodName(Method m) {
        return m.toString();
    }

    public  T proxy() {
        init();
        return (T) Proxy.newProxyInstance(HttpServiceProxy.class.getClassLoader(), new Class[]{clz}, (proxy, method, args) -> invoke(method,args));
    }


    public Request buildHttpRequest(Method method, Object args) {
//        String methodType = httpMethodTypes.get(getMethodName(method));
//        if (StringUtils.equalsIgnoreCase(methodType, "GET"))
//            return get(method, args);
        return post(method, args);
    }

    private String serialParam(Object[] args) {
        return JSON.toJSONString(args);
    }

    public Request post(Method method, Object args) {
            return new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=UTF-8"),
                            JSON.toJSONString(new JSONObject()
                                    .fluentPut("request",args)
                                    .fluentPut("service",clz.getSimpleName())
                                    .fluentPut("method",method.getName())
                            )))
                    .addHeader("Accept", "application/json")
                    .build();

//        FormEncodingBuilder builder = new FormEncodingBuilder()
//                .add("_service", clz.getSimpleName())
//                .add("_method", method.getName())
//                .add("_paramType", ParamFormat.Map.getValue());
//        serialize(method, args).entrySet().forEach(stringObjectEntry -> builder.add(stringObjectEntry.getKey(), stringObjectEntry.getValue()));
//        return new Request.Builder()
//                .url(url)
//                .post(builder.build())
//                .build();
    }

    public Request get(Method method, Object[] args) {
        HttpUrl.Builder url = new HttpUrl.Builder().scheme(scheme).addPathSegment(path).port(port).host(this.host);
        serialize(method, args).entrySet().forEach(stringObjectEntry -> url.addQueryParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue()));
        HttpUrl host = url
                .addQueryParameter("_service", clz.getSimpleName())
                .addQueryParameter("_method", method.getName())
                .addQueryParameter("_paramType", ParamFormat.Map.getValue()).build();
        return new Request.Builder().url(host).build();
    }

    private Map<String, String> serialize(Method method, Object[] args) {
        String[] parameterName = new DefaultParameterNameExtractor().getParameterName(method);
        System.out.println(parameterName);
        Map<String, String> map = Maps.newHashMap();
        if (args == null || args.length == 0)
            Validate.isTrue(parameterName.length == 0);
        if (parameterName == null)
            Validate.isTrue(args == null || args.length == 0);
        for (int i = 0; i < args.length; i++) {
            map.put(parameterName[i], args[i] == null ? null : JSON.toJSONString(args[i]));
        }
        return map;
    }

    public Object invoke(Method method, Object[] args) throws Throwable {
        String[] parameterName = new DefaultParameterNameExtractor().getParameterName(method);
        if (excludeMethods.contains(method.getName()))
            throw new IllegalAccessException("Object method are not supported");
        JSONObject req = new JSONObject();
        for (int i = 0; i < parameterName.length; i++) {
            req.put(parameterName[i],args[i]);
        }
        Response response = client.newCall(buildHttpRequest(method, req)).execute();
        if (response.isSuccessful()) {
            String re = response.body().string();
            if (StringUtils.isEmpty(re))
                return null;
            return JSON.parseObject(re, method.getReturnType());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDefaultHttpMethodType(String methodType) {
        if (StringUtils.isNotBlank(methodType))
            this.methodType = methodType.trim();
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public Class<T> getClz() {
        return clz;
    }
}
