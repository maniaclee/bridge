package com.lvbby.bridge.http.handler;

import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.api.ParamFormat;
import com.lvbby.bridge.api.ParamParsingContext;
import com.lvbby.bridge.exception.BridgeRoutingException;
import com.lvbby.bridge.gateway.ApiGateWayInitHandler;
import com.lvbby.bridge.gateway.Request;
import com.lvbby.bridge.http.HttpBridgeUtil;
import com.lvbby.bridge.http.HttpUserManager;
import com.lvbby.bridge.http.annotation.HttpUserIdParam;
import com.lvbby.bridge.util.BridgeUtil;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by lipeng on 2017/4/27.
 */
public class HttpUserIdInjectInitHandler implements ApiGateWayInitHandler {
    @Override
    public void handle(ParamParsingContext paramParsingContext) throws BridgeRoutingException {
        MethodParameter[] paramTypes = paramParsingContext.getApiMethod().getParamTypes();
        Request request = paramParsingContext.getRequest();
        if (ParamFormat.Map.getValue().equals(request.getParamType())) {
            Map<String, Object> params = (Map<String, Object>) request.getParam();
            for (MethodParameter paramType : paramTypes) {
                Parameter parameter = paramType.getMethod().getParameters()[paramType.getIndex()];
                HttpUserIdParam annotation = parameter.getAnnotation(HttpUserIdParam.class);
                if (annotation != null) {
                    HttpUserManager httpUserManager = HttpBridgeUtil.getHttpBridge(request).getHttpUserManager();
                    Validate.notNull(httpUserManager, "If you're using user related service , HttpUserManager can't be null");
                    String userId = httpUserManager.getUserId(HttpBridgeUtil.getHttpServletRequest(request));
                    Validate.notBlank(userId, "userId can't be empty");
                    if (String.class.equals(paramType.getType())) {
                        params.put(paramType.getName(), userId);
                    } else {
                        //注入userId到参数对象的属性中
                        Object target = params.get(paramType.getName());
                        if (target != null) {
                            Field field = BridgeUtil.getField(target.getClass(), annotation.propertyName());
                            Validate.notNull(field, String.format("unknown property %s in %s", annotation.propertyName(), target.getClass().getName()));
                            try {
                                field.set(target, userId);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else if (ParamFormat.Array.getValue().equals(request.getParamType())) {
        }
    }

    private String parseParamName(MethodParameter paramType, HttpUserIdParam annotation) {
        if (String.class.equals(paramType.getType())) {
            return paramType.getName();
        }
        return annotation.propertyName();
    }
}
