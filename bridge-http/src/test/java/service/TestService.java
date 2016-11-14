package service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by peng on 16/9/24.
 */
public interface TestService {

    void echo(String s);

    Map<String, String> handle(String s, String shit);

    Map<String, String> inject(String fuck, String you, HttpServletRequest httpServletRequest);
}
