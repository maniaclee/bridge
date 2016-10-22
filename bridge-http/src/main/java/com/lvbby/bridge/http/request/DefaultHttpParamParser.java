package com.lvbby.bridge.http.request;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.Params;
import com.lvbby.bridge.exception.BridgeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lipeng on 16/10/20.
 */
public class DefaultHttpParamParser implements HttpParamParser {


    private String paramAttributeName;

    public DefaultHttpParamParser(String paramAttributeName) {
        this.paramAttributeName = paramAttributeName;
    }

    @Override
    public Params parse(HttpServletRequest request, HttpServletResponse httpServletResponse, ApiService apiService, String method) throws Exception {
        String param = request.getParameter(paramAttributeName);
        List<ApiMethod> apiMethods = apiService.getApiMethods(method);
        if (apiMethods == null || apiMethods.isEmpty())
            throw new BridgeException(String.format("can't find value for %s.%s", apiService.getServiceName(), method));
        int size = JSON.parseArray(param).size();
        for (ApiMethod apiMethod : apiMethods) {
            Type[] types = getTypes(apiMethod);
            if (size == types.length) {
                List args = JSON.parseArray(param, types);
                if (apiMethod.getParamTypes().length > types.length) {
                    //need to inject args
                    inject(apiMethod, args, request);
                    inject(apiMethod, args, httpServletResponse);
                }
                return Params.of(args.toArray());
            }
        }
        return null;
    }

    private void inject(ApiMethod apiMethod, List dest, Object arg) {
        for (MethodParameter methodParameter : apiMethod.getParamTypes()) {
            if (methodParameter.getClass().equals(arg.getClass())) {
                dest.add(methodParameter.getIndex(), arg);
                return;
            }
        }
    }


    private Type[] getTypes(ApiMethod apiMethod) {
        List<Type> re = Lists.newArrayList();
        for (MethodParameter methodParameter : apiMethod.getParamTypes()) {
            if (!injectClass.contains(methodParameter.getType()))
                re.add(methodParameter.getType());
        }
        return re.toArray(new Type[0]);
    }


    private static Set<Class> injectClass = new HashSet<Class>();

    static {
        injectClass.add(HttpServletRequest.class);
        injectClass.add(HttpServletResponse.class);
    }
}
