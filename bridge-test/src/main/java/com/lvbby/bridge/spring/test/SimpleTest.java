package com.lvbby.bridge.spring.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

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

    public void getMethodInfo(String pkgName) {
        try {
            Class clazz = Class.forName(pkgName);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                System.out.println("方法名称:" + methodName);
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                    String parameterName = clas.getName();
                    System.out.println("参数名称:" + parameterName);
                }
                System.out.println("*****************************");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getMethodInfo(Class clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            System.out.println("方法名称:" + methodName);
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> clas : parameterTypes) {
                String parameterName = clas.getName();
                System.out.println("参数名称:" + parameterName);
            }
            System.out.println("*****************************");
        }
    }

    @Test
    public void dd() {
//        getMethodInfo("java.util.HashSet");
        getMethodInfo(HashSet.class);
    }

}
