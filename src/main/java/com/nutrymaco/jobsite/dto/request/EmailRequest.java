package com.nutrymaco.jobsite.dto.request;

import com.nutrymaco.jobsite.util.PatternChecker;

import java.util.regex.Pattern;

public class EmailRequest {
    private String email;

    public EmailRequest() {

    }

    public EmailRequest(String email) {
        setEmail(email);
    }

    public void setEmail(String email) {
        if (PatternChecker.checkEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("email does not match pattern");
        }
    }

    public String getEmail() {
        return email;
    }
}
