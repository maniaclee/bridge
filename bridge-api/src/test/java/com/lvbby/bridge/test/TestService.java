package com.lvbby.bridge.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.Map;

/**
 * Created by lipeng on 16/9/23.
 */
public class TestService {

    public void echo(String s) {
        System.out.println("fuck " + s);
    }

    public void echo(String s, String s2) {
        System.out.println("fuck echo multi " + s + s2);
    }

    public void run(String key, String value, int type) {
        System.out.println(Lists.newArrayList(key, value, type));
    }

    public void signle(BridgeParamTest request){
        System.out.println(JSON.toJSONString(request));
    }
    public void multi(BridgeParamTest s, Map map){
        System.out.println(JSON.toJSONString(s));
        System.out.println(JSON.toJSONString(map));
    }
}
