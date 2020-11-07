package com.nutrymaco.jobsite.util;

public class Random {

    public static String getString(int length) {
        String chars = "qwertyuiopasdfghjklzxcvbnm" +
                "QWERTYUIOPASDFGHJKLZXCVBNM" +
                "1234567890";
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int n = (int) Math.round(Math.random() * length) % chars.length();
            string.append(chars.charAt(n));
        }
        return string.toString();
    }

}
