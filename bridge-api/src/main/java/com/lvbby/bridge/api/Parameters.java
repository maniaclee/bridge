package com.lvbby.bridge.api;

import java.util.Arrays;

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
