package com.nutrymaco.jobsite.util;

import java.security.SecureRandom;

public class Random {

    private final static SecureRandom random = new SecureRandom();

    public static String getString(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return String.valueOf(bytes);
    }

}
