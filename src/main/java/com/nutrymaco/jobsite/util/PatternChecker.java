package com.nutrymaco.jobsite.util;

public class PatternChecker {

    private static final String EMAIL_PATTERN = "[a-zA-Z0-9_]+@[a-z]\\.(ru|com|org)";

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }
}
