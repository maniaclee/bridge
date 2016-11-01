package com.lvbby.bridge.http.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by lipeng on 16/8/21.
 */
public class RandomGenerator {

    public static final String number = "0123456789";
    public static final String alpha_lowerCase = "abcdefghijklmnopqrstuvwxyz";
    public static final String alpha_upperCase = alpha_lowerCase.toUpperCase();

    public static final RandomGenerator instance = new RandomGenerator(Lists.newArrayList(number, alpha_lowerCase, alpha_upperCase), 4);
    private char[] randomCodes;
    private int length;

    public RandomGenerator(char[] randomCodes, int length) {
        this.randomCodes = randomCodes;
        this.length = length;
    }

    public RandomGenerator(String randomCodes, int length) {
        this(randomCodes.toCharArray(), length);
    }

    public RandomGenerator(List<String> ss, int length) {
        this(StringUtils.join(ss, ""), length);
    }

    public String gen() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(randomCodes[((int) (Math.random() * 10000000)) % randomCodes.length]);
        }
        String s = stringBuilder.toString();
        return s;
    }

}
