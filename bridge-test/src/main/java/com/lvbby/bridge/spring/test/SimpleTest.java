package com.lvbby.bridge.spring.test;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

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
    public void dd() throws URISyntaxException {
        String url = "one=1&two=2&three=3&three=3a";
//        List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");
        List<NameValuePair> params = URLEncodedUtils.parse(url, Charset.forName("UTF-8"));

        for (NameValuePair param : params) {
            System.out.println(param.getName() + " : " + param.getValue());
        }
    }

}
