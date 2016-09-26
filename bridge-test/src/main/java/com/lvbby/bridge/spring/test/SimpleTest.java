package com.lvbby.bridge.spring.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by lipeng on 16/9/26.
 */
public class SimpleTest {

    @Test
    public void sdfsf() {
        Object[] objects = {"123", 1232222, new HashMap<String, String>()};
        System.out.println(JSON.toJSONString(objects));
        // http://localhost:8080/data/TestService/echo?param=["123"];
        //  http://localhost:8080/data/TestService/handle?param=["123","fuck"]
        System.out.println(JSON.toJSONString("sdf"));
    }


}
