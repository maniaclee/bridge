package com.lvbby.bridge.api.impl;

import com.google.common.collect.Lists;
import com.lvbby.bridge.api.ApiMethod;
import com.lvbby.bridge.api.ApiMethodBuilder;
import com.lvbby.bridge.api.ApiService;
import com.lvbby.bridge.gateway.Request;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;


/**
 * Created by peng on 16/9/22.
 */
public class ApiMethodReflectionBuilder implements ApiMethodBuilder {
    /***
     * exclude the java interfaces and Object
     */
    private List<String> excludePackage = Lists.newArrayList("java");

    @Override
    public List<ApiMethod> getMethods(ApiService service) {
        List<ApiMethod> re = Lists.newLinkedList();
        for (Method m : extractMethods(service.getService().getClass())) re.add(new DefaultApiMethod(m).init());
        return re;
    }

    public List<Method> extractMethods(Class clz) {
        List<Method> re = Lists.newLinkedList();
        /** TODO javassist can't get method parameter from interface !!! */
        //        if (fromInterface(clz))
        //            re.addAll(extractMethodsFromInterface(clz));
        //        else
        re.addAll(extratMethodsFromClass(clz));
        return re;
    }

    /***
     * get method from interface or class
     *
     * @param clz
     * @return
     */
    private boolean fromInterface(Class clz) {
        Class[] interfaces = clz.getInterfaces();
        if (interfaces.length > 0)
            for (Class in : interfaces)
                if (!isExcluded(in))
                    return true;
        return false;
    }

    public List<Method> extractMethodsFromInterface(Class clazz) {
        List<Method> re = Lists.newLinkedList();
        Class[] interfaces = clazz.getInterfaces();
        for (Class in : interfaces) {
            re.addAll(getNeededMethods(in));
        }
        return re;
    }

    public List<Method> extratMethodsFromClass(Class clz) {
        List<Method> re = Lists.newLinkedList();
        while (clz != null) {
            re.addAll(getNeededMethods(clz));
            clz = clz.getSuperclass();
        }
        return re;
    }

    public List<Method> getNeededMethods(Class clz) {
        List<Method> re = Lists.newLinkedList();
        if (!isExcluded(clz))
            for (Method m : clz.getDeclaredMethods())
                if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()))
                    re.add(m);
        return re;
    }

    private boolean isExcluded(Class clz) {
        for (String pack : excludePackage)
            if (clz.getName().startsWith(pack))
                return true;
        return false;
    }


    public static void main(String[] args) {
        new ApiMethodReflectionBuilder().extractMethods(ClassNameServiceNameExtractor.class).forEach(s -> System.out.println(s.getName()));
        new ApiMethodReflectionBuilder().extractMethods(Request.class).forEach(s -> System.out.println(s.getName()));
    }
}
