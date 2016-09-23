package com.lvbby.bridge.api.test;

/**
 * Created by lipeng on 16/9/23.
 */
public class TestService {

    public void echo(String s) {
        System.out.println("fuck " + s);
    }

    public void echo(String s, String s2) {
        System.out.println("fuck echo multi " + s + s2);
    }
}
