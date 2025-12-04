package com.example.Healthify.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequestDTO {

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min=6)
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
