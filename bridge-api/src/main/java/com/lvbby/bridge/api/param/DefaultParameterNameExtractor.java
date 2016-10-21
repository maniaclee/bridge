package com.lvbby.bridge.api.param;

import com.lvbby.bridge.api.ParameterNameExtractor;

import java.lang.reflect.Method;

/**
 * Created by lipeng on 16/9/23.
 */
public class DefaultParameterNameExtractor implements ParameterNameExtractor {
    private AsmParameterNameExtractor asmParameterNameExtractor = new AsmParameterNameExtractor();
    private JdkParameterNameExtractor jdkParameterNameExtractor = new JdkParameterNameExtractor();

    @Override
    public String[] getParameterName(Method method) {
        return jdkParameterNameExtractor.getParameterName(method);
    }

}
