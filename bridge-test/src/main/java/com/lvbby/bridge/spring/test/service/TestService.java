package com.lvbby.bridge.spring.test.service;

import java.util.Map;

/**
 * Created by peng on 16/9/24.
 */
public interface TestService {

    void echo(String s);

    Map<String, String> handle(String s, String shit);

}
