package com.example.Healthify.services;

import com.example.Healthify.models.dtos.SignInRequestDTO;
import com.example.Healthify.models.dtos.SignInResponseDTO;

public interface AuthService {

    SignInResponseDTO signIn(SignInRequestDTO requestDTO);

}
