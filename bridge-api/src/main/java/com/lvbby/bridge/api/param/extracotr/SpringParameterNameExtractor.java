package com.lvbby.bridge.api.param.extracotr;

import com.lvbby.bridge.api.ParameterNameExtractor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * Created by lipeng on 17/4/21.
 */
public class SpringParameterNameExtractor extends LocalVariableTableParameterNameDiscoverer implements ParameterNameExtractor {
    @Override
    public String[] getParameterName(Method method) {
        return super.getParameterNames(method);
    }
}
