package com.lvbby.bridge.api;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.lvbby.bridge.util.BridgeUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lipeng on 16/9/23.
 * wrapper for parameters
 */
public class Parameters {
    public static final String byIndex = "index";
    public static final String byName = "name";
    private List<Parameter> parameters = Lists.newArrayList();
    /***
     * route type for finding the parameter
     */
    private String type = byIndex;
    private ApiMethod apiMethod;

    private Parameters(ApiMethod apiMethod, String type) {
        this.apiMethod = apiMethod;
        this.type = type;
    }

    public static Parameters of(ApiMethod apiMethod, Object[] objects) {
        Parameters re = new Parameters(apiMethod, byIndex);
        if (objects != null && objects.length > 0) {
            List<Parameter> parameters = Lists.newArrayList();
            for (int i = 0; i < objects.length; i++)
                parameters.add(new Parameter(objects[i], i));
            re.setParameters(parameters);
        }
        return re;
    }

    /**
     * parameters as map
     *
     * @param map
     * @return
     */
    public static Parameters ofMap(ApiMethod apiMethod, Map map) {
        Parameters re = new Parameters(apiMethod, byName);
        re.setType(byName);
        if (map != null) {
            List<Parameter> list = Lists.newArrayList();
            for (Object key : map.keySet()) {
                list.add(new Parameter(map.get(key), key.toString()));
            }
            re.setParameters(list);
        }
        return re;
    }

    public Object[] toParameters() {
        final Object[] re = newRawResultWithDefaultValue();
        //void
        if ((parameters == null || parameters.isEmpty()))
            return re;

        //match by index
        if (Objects.equal(getType(), Parameters.byIndex)) {
            for (Parameter parameter : parameters) {
                re[parameter.getIndex()] = parameter.getParam();
            }
            return re;
        }
        //match by name
        if (Objects.equal(getType(), Parameters.byName)) {
            Map<String, Object> map = parameters.stream().collect(Collectors.toMap(p -> p.getName(), p -> p.getParam()));
            for (int i = 0; i < apiMethod.getParamTypes().length; i++) {
                Object v = map.get(apiMethod.getParamTypes()[i].getName());
                if (v != null)
                    re[i] = v;
            }
            return re;
        }
        throw new IllegalArgumentException("unknown parameter type : " + getType());
    }


    private Object[] newRawResultWithDefaultValue() {
        MethodParameter[] parameters = apiMethod.getParamTypes();
        Object[] re = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            re[i] = BridgeUtil.defaultValueForType(parameters[i].getType());
        }
        return re;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
