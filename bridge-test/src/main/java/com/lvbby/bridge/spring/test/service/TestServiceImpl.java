package com.lvbby.bridge.spring.test.service;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 16/9/24.
 */
@Service
public class TestServiceImpl implements TestService {
    public void echo(String s) {
        System.out.println("fuck " + s);
    }

    public Map<String, String> handle(String s, String shit) {
        HashMap<String, String> re = Maps.newHashMap();
        re.put(s, shit);
        return re;
    }
}
