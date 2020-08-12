package com.nutrymaco.jobsite.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userName;
    private String password;
    private String secret;
}
