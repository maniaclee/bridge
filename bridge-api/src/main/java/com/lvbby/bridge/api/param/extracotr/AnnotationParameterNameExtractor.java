package com.lvbby.bridge.api.param.extracotr;

import com.lvbby.bridge.annotation.BridgeParam;
import com.lvbby.bridge.api.ParameterNameExtractor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/10/30.
 */
public class AnnotationParameterNameExtractor implements ParameterNameExtractor {
    @Override
    public String[] getParameterName(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        String[] re = new String[parameterAnnotations.length];
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            if (parameterAnnotation != null)
                for (Annotation annotation : parameterAnnotation) {
                    if (BridgeParam.class.equals(annotation))
                        re[i] = ((BridgeParam) annotation).value();
                    break;
                }
        }
        return re;
    }
}
