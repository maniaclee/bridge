package com.lvbby.bridge.http.admin;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.api.MethodParameter;
import com.lvbby.bridge.gateway.ApiGateWay;
import com.lvbby.bridge.http.admin.dto.ApiMethodDTO;

import java.util.List;

/**
 * Created by lipeng on 16/10/22.
 */
public class HttpBridgeService {
    private ApiGateWay apiGateWay;

    public HttpBridgeService(ApiGateWay apiGateWay) {
        this.apiGateWay = apiGateWay;
    }

    public List<ApiMethodDTO> getApiMethods() {
        List<ApiMethodDTO> re = Lists.newArrayList();
        for (ApiService apiService : apiGateWay.getAllApiServices()) {
            re.addAll(extractApiMethods(apiService));
        }
        return re;
    }

    private List<ApiMethodDTO> extractApiMethods(ApiService apiService) {
        List<ApiMethodDTO> re = Lists.newLinkedList();
        List<ApiMethod> ms = apiService.getAllApiMethods();
        for (ApiMethod m : ms) {
            ApiMethodDTO apiMethodDTO = new ApiMethodDTO();
            apiMethodDTO.setService(apiService.getServiceName());
            apiMethodDTO.setName(m.getName());
            List<String> paramNames = Lists.newLinkedList();
            List<String> paramTypes = Lists.newLinkedList();
            for (MethodParameter methodParameter : m.getParamTypes()) {
                paramNames.add(methodParameter.getIndex(), methodParameter.getName());
                paramTypes.add(methodParameter.getIndex(), methodParameter.getType().getSimpleName());
            }
            apiMethodDTO.setParamNames(paramNames);
            apiMethodDTO.setParamTypes(paramTypes);
            re.add(apiMethodDTO);
        }
        return re;
    }
}
