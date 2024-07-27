package com.roomy.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
