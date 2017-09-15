package com.lvbby.bridge.http.handler;

import com.google.common.collect.Lists;
import com.lvbby.bridge.handler.common.LogHandler;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lipeng on 2017/9/15.
 */
public class HttpLogHanlder extends LogHandler {
    private static List<Class> exclude = Lists.newArrayList(HttpServletRequest.class, HttpServletResponse.class);

    public HttpLogHanlder(Logger logger) {
        super(logger);
    }

    @Override
    public String doSerialize(Object parameter) {
        if (exclude.contains(parameter.getClass())) {
            return "";
        }
        return super.doSerialize(parameter);
    }
}
