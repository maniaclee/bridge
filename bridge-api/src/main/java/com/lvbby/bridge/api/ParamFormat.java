package com.lvbby.bridge.api;

/**
 * Created by lipeng on 16/10/20.
 */
public enum ParamFormat {
    /***
     * json Array: like [json,json,json,json]
     * because method parameters are array
     */
    JSON_ARRAY("json_array"),
    /***
     * pure json
     */
    JSON("json"),
    /***
     * key value pairs: like  key1:value1
     * if method parameters each have a name
     */
    MAP("map"),
    /***
     * like map, but ignore the error properties
     */
    MAP_BEST_MATCH("map_best_match"),
    /***
     * normal parameter args
     */
    NORMAL("normal");
    private String value;

    ParamFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
