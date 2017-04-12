package com.lvbby.bridge.api;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvbby.bridge.gateway.InjectProcessor;
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
    private TreeMap<Integer, Object> injects = Maps.newTreeMap();

    public void addInjectValue(int index, Object value) {
        injects.put(index, value);
    }

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
        addInjectValue();
        final Object[] re = newRawResultWithDefaultValue();
        //注入
        injects.entrySet().forEach(entry -> re[entry.getKey()] = entry.getValue());
        //void
        if ((parameters == null || parameters.isEmpty()))
            return re;

        //match by index
        if (Objects.equal(getType(), Parameters.byIndex)) {
            //注入，跳过injects的位置
            padding(parameters, re, injects.keySet());
            //            parameters.forEach(parameter -> re[parameter.getIndex()] = parameter.getParam());
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

    /**
     * 跳过某些位置将 src 插入到dest中
     *
     * @param src
     * @param dest
     * @param existedIndices
     */
    private static void padding(List src, Object[] dest, Collection<Integer> existedIndices) {
        Iterator<Integer> iterator = existedIndices.iterator();
        for (int i = 0, offset = 0, existedIndex = iterator.next(); i < src.size(); i++) {
            if (i + offset == existedIndex) {
                ++offset;
                if (iterator.hasNext())
                    existedIndex = iterator.next();
            }
            dest[i + offset] = src.get(i);
        }
    }

    public static void main(String[] args) {
        List a = Lists.newArrayList(1, 2, 3, 4, 5);
        Object[] dest = new Object[10];
        Collection<Integer> ins = Lists.newArrayList(1, 3);
        padding(a, dest, ins);
        System.out.println(Arrays.asList(dest));
    }

    private void addInjectValue() {
        for (int i = 0; i < apiMethod.getParamTypes().length; i++) {
            Class type = apiMethod.getParamTypes()[i].getType();
            Object injectValueByType = InjectProcessor.getInjectValueByType(type);
            if (injectValueByType != null) {
                injects.put(i, injectValueByType);
            }
        }
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
