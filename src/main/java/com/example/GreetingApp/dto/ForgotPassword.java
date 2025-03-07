package com.example.GreetingApp.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPassword {
    @NotBlank(message = "Password cannot be empty")
    private String password;
}