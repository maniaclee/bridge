package service;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 16/9/24.
 */
public class TestServiceImpl implements TestService {
    public void echo(String s) {
        System.out.println("fuck " + s);
    }

    public Map<String, String> handle(String s, String shit) {
        HashMap<String, String> re = Maps.newHashMap();
        re.put(s, shit);
        return re;
    }

    @Override
    public Map<String, String> inject(String s, String b, HttpServletRequest httpServletRequest) {
        HashMap<String, String> re = Maps.newHashMap();
        re.put(s, b);
        re.put("inject", httpServletRequest.toString());
        return re;
    }
}
