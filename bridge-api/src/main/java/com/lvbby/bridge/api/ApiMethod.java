package com.lvbby.bridge.api;

import com.google.common.collect.Maps;
import com.lvbby.bridge.exception.BridgeInvokeException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lipeng on 16/10/19.
 */
public interface ApiMethod {

    String getName();

    Object invoke(ApiService apiService, Object[] parameters) throws BridgeInvokeException;

    MethodParameter[] getParamTypes();

    default Map<String,MethodParameter> getParamAsMap(){
        MethodParameter[] paramTypes = getParamTypes();
        if(paramTypes==null||paramTypes.length==0){
            return Maps.newHashMap();
        }
        return Arrays.stream(paramTypes).collect(Collectors.toMap(MethodParameter::getName,Function.identity()));
    }
}
