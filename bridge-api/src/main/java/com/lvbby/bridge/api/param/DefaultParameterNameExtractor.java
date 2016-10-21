package com.lvbby.bridge.api.param;

import com.lvbby.bridge.api.ParameterNameExtractor;

import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/9/23.
 */
public class DefaultParameterNameExtractor implements ParameterNameExtractor {
    private ParameterNameExtractor asmParameterNameExtractor = new AsmParameterNameExtractor();
    private ParameterNameExtractor jdkParameterNameExtractor = new JdkParameterNameExtractor();
    private ParameterNameExtractor  javassistParameterNameExtractor= new JavassistParameterNameExtractor();

    @Override
    public String[] getParameterName(Method method) {
        return javassistParameterNameExtractor.getParameterName(method);
    }

}
