package com.lvbby.bridge.api;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by lipeng on 16/9/23.
 * wrapper for parameters
 */
public class Parameters {
    public static final String byIndex = "index";
    public static final String byName = "name";
    private Parameter[] parameters;
    /***
     * route type for finding the parameter
     */
    private String type = byIndex;

    public static Parameters of(Object[] objects) {
        if (objects == null)
            return new Parameters(null);
        Parameter[] parameters = new Parameter[objects.length];
        for (int i = 0; i < objects.length; i++)
            parameters[i] = new Parameter(objects[i]);
        return new Parameters(parameters);
    }

    /**
     * parameters as map
     *
     * @param map
     * @return
     */
    public static Parameters ofMap(Map map) {
        Parameters re = new Parameters();
        re.setType(byName);
        if (map != null) {
            List<Parameter> list = Lists.newArrayList();
            for (Object key : map.keySet()) {
                list.add(new Parameter(map.get(key), key.toString()));
            }
            re.setParameters(list.toArray(new Parameter[0]));
        }
        return re;
    }

    public Parameters() {
    }

    public Parameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public Parameters(Parameter[] parameters, String type) {
        this.parameters = parameters;
        this.type = type;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Params{" +
                "params=" + Arrays.toString(parameters) +
                ", type='" + type + '\'' +
                '}';
    }
}
