package com.roomy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotEmpty(message = "ユーザー名は必須です。")
    private String userName;
    @Size(min = 6, max = 20, message = "英数字6文字以上、20文字以下に設定してください。")
    @NotEmpty(message = "パスワードは必須です。")
    private String password;
    @NotEmpty(message = "名前は必須です。")
    private String firstName;
    @NotEmpty(message = "苗字は必須です。")
    private String lastName;
    @NotEmpty(message = "メールは必須です。")
    @Email
    private String email;
}
