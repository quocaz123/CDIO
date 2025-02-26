package com.example.CDIO.domain.dto;

import com.example.CDIO.util.validator.RegisterChecked;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterChecked
public class RegisterDTO {
    private String name;
    @Email(message = "Email không hợp lệ", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @Size(min = 3, message = "Password phải có tối thiểu 3 ký tự")
    private String password;
    private String confirmPassword;
}
