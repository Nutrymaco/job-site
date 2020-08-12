package com.nutrymaco.jobsite.dto.request;

import lombok.Data;

@Data
public class AuthenticateRequest {
    String username;
    String password;
}
